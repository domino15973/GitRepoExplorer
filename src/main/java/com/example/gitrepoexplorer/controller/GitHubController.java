package com.example.gitrepoexplorer.controller;

import com.example.gitrepoexplorer.model.Repository;
import com.example.gitrepoexplorer.service.GitHubClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GitHubController {

    private final GitHubClient gitHubClient;

    @Autowired
    public GitHubController(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    @GetMapping("/repos/{username}")
    public Mono<ResponseEntity<List<Repository>>> getUserRepositories(@PathVariable String username) {
        return gitHubClient.getUserRepositories(username)
            .flatMap(repo -> gitHubClient.getRepositoryBranches(username, repo.getName())
                .collectList()
                .map(branches -> {
                    repo.setBranches(branches);
                    return repo;
                }))
            .collectList()
            .map(ResponseEntity::ok);
    }
}