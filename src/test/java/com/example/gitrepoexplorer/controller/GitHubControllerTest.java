package com.example.gitrepoexplorer.controller;

import com.example.gitrepoexplorer.model.Branch;
import com.example.gitrepoexplorer.model.Commit;
import com.example.gitrepoexplorer.model.Repository;
import com.example.gitrepoexplorer.service.GitHubClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.List;

@WebFluxTest(GitHubController.class)
public class GitHubControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GitHubClient gitHubClient;

    @Test
    public void testGetUserRepositories() {
        // Arrange
        Repository repo = new Repository();
        repo.setName("test-repo");
        repo.setFork(false);

        Branch branch = new Branch();
        branch.setName("main");
        branch.setCommit(new Commit());

        // Mocking the behavior of GitHubClient
        Mockito.when(gitHubClient.getUserRepositories("testuser"))
                .thenReturn(Flux.just(repo));

        Mockito.when(gitHubClient.getRepositoryBranches("testuser", "test-repo"))
                .thenReturn(Flux.just(branch));

        // Act & Assert
        webTestClient.get()
                .uri("/api/github/repos/testuser")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Repository.class)
                .consumeWith(response -> {
                    List<Repository> repositories = response.getResponseBody();
                    assert repositories != null;
                    assert repositories.size() == 1;
                    assert repositories.get(0).getName().equals("test-repo");
                    assert repositories.get(0).getBranches().size() == 1;
                    assert repositories.get(0).getBranches().get(0).getName().equals("main");
                });
    }
}
