package com.example.gitrepoexplorer.service;

import com.example.gitrepoexplorer.model.Branch;
import com.example.gitrepoexplorer.model.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class GitHubClient {

    private static final Logger logger = LogManager.getLogger(GitHubClient.class);

    private final WebClient webClient;

    @Autowired
    public GitHubClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<Repository> getUserRepositories(String username) {
        logger.info("Fetching repositories for user: {}", username);
        return webClient.get()
            .uri("/users/{username}/repos", username)
            .retrieve()
            .bodyToFlux(Repository.class)
            .filter(repo -> !repo.isFork())
            .doOnError(e -> logger.error("Error fetching repositories for user: {}", username, e));
    }

    public Flux<Branch> getRepositoryBranches(String username, String repoName) {
        logger.info("Fetching branches for repository: {} owned by user: {}", repoName, username);
        return webClient.get()
            .uri("/repos/{username}/{repo}/branches", username, repoName)
            .retrieve()
            .bodyToFlux(Branch.class)
            .doOnError(e -> logger.error("Error fetching branches for repository: {} owned by user: {}", repoName, username, e));
    }
}
