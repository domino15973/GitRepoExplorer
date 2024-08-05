package com.example.gitrepoexplorer.service;

import com.example.gitrepoexplorer.model.Branch;
import com.example.gitrepoexplorer.model.Commit;
import com.example.gitrepoexplorer.model.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class GitHubClientTest {

    private GitHubClient gitHubClient;
    private WebClient webClient;

    @BeforeEach
    public void setUp() {
        webClient = Mockito.mock(WebClient.class, Mockito.RETURNS_DEEP_STUBS);
        gitHubClient = new GitHubClient(webClient);
    }

    @Test
    public void testGetUserRepositories() {
        // Arrange
        Repository repo = new Repository();
        repo.setName("test-repo");
        repo.setFork(false);

        // Mocking WebClient's behavior
        when(webClient.get()
                .uri(anyString(), eq("testuser"))
                .retrieve()
                .bodyToFlux(Repository.class))
                .thenReturn(Flux.just(repo));

        // Act
        Flux<Repository> result = gitHubClient.getUserRepositories("testuser");

        // Assert
        List<Repository> repos = result.collectList().block();
        assert repos != null;
        assertEquals(1, repos.size());
        assertEquals("test-repo", repos.get(0).getName());
    }

    @Test
    public void testGetRepositoryBranches() {
        // Arrange
        Branch branch = new Branch();
        branch.setName("main");
        branch.setCommit(new Commit());

        // Mocking WebClient's behavior
        when(webClient.get()
                .uri(anyString(), eq("testuser"), eq("test-repo"))
                .retrieve()
                .bodyToFlux(Branch.class))
                .thenReturn(Flux.just(branch));

        // Act
        Flux<Branch> result = gitHubClient.getRepositoryBranches("testuser", "test-repo");

        // Assert
        List<Branch> branches = result.collectList().block();
        assert branches != null;
        assertEquals(1, branches.size());
        assertEquals("main", branches.get(0).getName());
    }
}
