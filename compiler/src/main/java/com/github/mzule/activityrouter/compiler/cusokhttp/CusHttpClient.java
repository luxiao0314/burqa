package com.github.mzule.activityrouter.compiler.cusokhttp;

/**
 * @Description 自定义OkHttp
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/4/22 10:09
 * @Version
 */
public class CusHttpClient {
    public Response sendRequest(Request request){
        return executeRequest(request);
    }

    private Response executeRequest(Request request) {
//        Socket socket = new Socket(request.url,80);
//        socket.connect();
        ResponseData data = null;
        return new Response(data);
    }

    public static void main(String[] args) {
//        new Request().url("")
    }
}
