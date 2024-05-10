package com.example.githubcodingexercise.data.network.client

import com.example.githubcodingexercise.data.network.model.Contributor
import com.example.githubcodingexercise.data.network.model.GitHubRepoResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubClient {
    @GET("search/repositories?q=stars:>0&per_page=100")
    suspend fun getTopRepositories(@Header("Authorization") token: String): Response<GitHubRepoResults>

    @GET("repos/{owner}/{repo}/contributors")
    suspend fun getTopContributorForRepository(@Header("Authorization") token: String, @Path("owner") owner: String, @Path("repo") repo: String, @Query("per_page") perPage: Int, @Query("page") page: Int): Response<List<Contributor>>
}