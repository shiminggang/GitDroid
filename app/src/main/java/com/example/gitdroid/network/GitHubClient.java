package com.example.gitdroid.network;

import com.example.gitdroid.github.repolist.model.Repo;
import com.example.gitdroid.login.model.Token;
import com.example.gitdroid.login.TokenInterceptor;
import com.example.gitdroid.login.model.User;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * GitHub客户端（Retrofit单例、初始化、实现接口、构建请求）
 */

public class GitHubClient implements GitHubApi {

    private static GitHubClient gitHubClient;
    private final GitHubApi gitHubApi;

    public static synchronized GitHubClient getInstance() {
        if (gitHubClient == null) {
            gitHubClient = new GitHubClient();
        }
        return gitHubClient;
    }

    private GitHubClient() {
        //拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //初始化OkHttpClient（添加HttpLoggingIntercepter和自定义拦截器）
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new TokenInterceptor())
                .build();

        //初始化Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        //构建请求
        gitHubApi = retrofit.create(GitHubApi.class);
    }

    /**
     * Token请求注解
     *
     * @param clientId
     * @param clientSecret
     * @param code
     * @return
     */
    @Override
    public Call<Token> getAccessToken(@Field("client_id") String clientId, @Field("client_secret") String clientSecret, @Field("code") String code) {
        return gitHubApi.getAccessToken(clientId, clientSecret, code);
    }

    /**
     * 用户信息请求注解
     *
     * @return
     */
    @Override
    public Call<User> getUserMsg() {
        return gitHubApi.getUserMsg();
    }

    /**
     * 搜索仓库
     *
     * @param language 语言类型
     * @param page     页数
     * @return
     */
    @Override
    public Call<Repo> getRepoList(@Query("q") String language, @Query("page") int page) {
        return gitHubApi.getRepoList(language, page);
    }
}
