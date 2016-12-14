package com.example.gitdroid.network;

import com.example.gitdroid.github.repolist.model.Repo;
import com.example.gitdroid.login.model.Token;
import com.example.gitdroid.login.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by smg on 2016/12/8.
 */

public interface GitHubApi {

    //在GitHub平台上注册APP返回的应用信息
    String CLIENT_ID = "eed73344d7e227ee94f5";
    String CLIENT_SECRET = "fb5972d2f58cbcf5031ce5fad3286a40f82d9056";
    String AUTH_SCOPE = "user,public_repo,repo";

    //Call Back
    String CALL_BACK = "zxkj";

    //Webview登录路径
    String AUTH_URL = "https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID + "&scope=" + AUTH_SCOPE;

    //Token请求Api
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("https://github.com/login/oauth/access_token")
    Call<Token> getAccessToken(@Field("client_id") String clientId, @Field("client_secret") String clientSecret, @Field("code") String code);

    //用户信息请求Api
    @GET("/user")
    Call<User> getUserMsg();

    //搜索仓库Api
    @GET("/search/repositories")
    Call<Repo> getRepoList(@Query("q") String language, @Query("page") int page);
}
