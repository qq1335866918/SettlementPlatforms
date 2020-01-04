package com.tt1000.settlementplatform.feature.network;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.tt1000.settlementplatform.utils.MyConstant;
import com.tt1000.settlementplatform.utils.SSLSocketClient;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.tt1000.settlementplatform.utils.MyConstant.gSharedPre;

public class LocalRetrofit {
    private static Retrofit mRetrofit;
    private static Retrofit.Builder build;
    // 避免相同url时，还去创建一个新的对象
    private static String oldUrl = "";

    public static Retrofit getInstance(String url) {
        url = checkUrl(url);
        try {
            if (build == null) {
                HttpLoggingInterceptor loggingInterceptor = null;
                if (loggingInterceptor == null) {
                    loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
//                            打印retrofit日志
                            try {
                                Log.i("cxy", "retrofitBack = " + message);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                            StringBuilder mMessage = new StringBuilder();
//
//                            // 请求或者响应开始
//                            if (message.startsWith("--> POST")) {
//                                mMessage.setLength(0);
//                            }
//                            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
//                            if ((message.startsWith("{") && message.endsWith("}"))
//                                    || (message.startsWith("[") && message.endsWith("]"))) {
//                                message = JsonUtil.formatJson(JsonUtil.decodeUnicode(message));
//                            }
//                            mMessage.append(message.concat("\n"));
//                            // 响应结束，打印整条日志
//                            if (message.startsWith("<-- END HTTP")) {
////                                LogUtil.d(mMessage.toString());
//                                Log.d(TAG, mMessage.toString() );
//                            }
                        }
                    });
                }
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient.Builder client = new OkHttpClient
                        .Builder();
                client.addInterceptor(loggingInterceptor);
                client.sslSocketFactory(SSLSocketClient.getSSLSocketFactory());
                client.hostnameVerifier(SSLSocketClient.getHostnameVerifier());
                build = new Retrofit.Builder()
                        .baseUrl(url)
                        .client(client.build())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addConverterFactory(new NullOnEmptyConverterFactory());
                mRetrofit = build.build();
                return mRetrofit;
            } else {
                // 如果这次申请的url和之前的url不同，则重新创建一个对象
                if (!oldUrl.equals(url)) {
                    oldUrl = url;
                    build.baseUrl(url);
                    mRetrofit = build.build();
                    return mRetrofit;
                }
                return mRetrofit;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Retrofit getInstance() {
        return getInstance("");
    }

    public static ApiService createService() {
        return getInstance().create(ApiService.class);
    }

    public static String checkUrl(String url) {
        if (url.isEmpty() || url == null) {
            String ip = gSharedPre.getString(MyConstant.SP_Server_IP, "");
            String port = gSharedPre.getString(MyConstant.SP_Server_PORT, "");
            if (TextUtils.isEmpty(ip) || TextUtils.isEmpty(ip)) {
                return "";
            }
            url = ip + ":" + port + "/";
        }
        return url;
    }

    public static class NullOnEmptyConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, final Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return new Converter<ResponseBody, Object>() {
                @Override
                public Object convert(ResponseBody body) throws IOException {
                    Gson gson = new Gson();
                    if (body.contentLength() == 0) {
                        return null;
                    }
//                    return gson.toJson(body.string());
                    return delegate.convert(body);
                }
            };
        }
    }

    static class JsonUtil {
        /**
         * 格式化json字符串
         *
         * @param jsonStr 需要格式化的json串
         * @return 格式化后的json串
         */
        public static String formatJson(String jsonStr) {
            if (null == jsonStr || "".equals(jsonStr)) return "";
            StringBuilder sb = new StringBuilder();
            char last = '\0';
            char current = '\0';
            int indent = 0;
            for (int i = 0; i < jsonStr.length(); i++) {
                last = current;
                current = jsonStr.charAt(i);
                //遇到{ [换行，且下一行缩进
                switch (current) {
                    case '{':
                    case '[':
                        sb.append(current);
                        sb.append('\n');
                        indent++;
                        addIndentBlank(sb, indent);
                        break;
                    //遇到} ]换行，当前行缩进
                    case '}':
                    case ']':
                        sb.append('\n');
                        indent--;
                        addIndentBlank(sb, indent);
                        sb.append(current);
                        break;
                    //遇到,换行
                    case ',':
                        sb.append(current);
                        if (last != '\\') {
                            sb.append('\n');
                            addIndentBlank(sb, indent);
                        }
                        break;
                    default:
                        sb.append(current);
                }
            }
            return sb.toString();
        }

        /**
         * 添加space
         *
         * @param sb
         * @param indent
         */
        private static void addIndentBlank(StringBuilder sb, int indent) {
            for (int i = 0; i < indent; i++) {
                sb.append('\t');
            }
        }

        /**
         * http 请求数据返回 json 中中文字符为 unicode 编码转汉字转码
         *
         * @param theString
         * @return 转化后的结果.
         */
        public static String decodeUnicode(String theString) {
            char aChar;
            int len = theString.length();
            StringBuffer outBuffer = new StringBuffer(len);
            for (int x = 0; x < len; ) {
                aChar = theString.charAt(x++);
                if (aChar == '\\') {
                    aChar = theString.charAt(x++);
                    if (aChar == 'u') {
                        int value = 0;
                        for (int i = 0; i < 4; i++) {
                            aChar = theString.charAt(x++);
                            switch (aChar) {
                                case '0':
                                case '1':
                                case '2':
                                case '3':
                                case '4':
                                case '5':
                                case '6':
                                case '7':
                                case '8':
                                case '9':
                                    value = (value << 4) + aChar - '0';
                                    break;
                                case 'a':
                                case 'b':
                                case 'c':
                                case 'd':
                                case 'e':
                                case 'f':
                                    value = (value << 4) + 10 + aChar - 'a';
                                    break;
                                case 'A':
                                case 'B':
                                case 'C':
                                case 'D':
                                case 'E':
                                case 'F':
                                    value = (value << 4) + 10 + aChar - 'A';
                                    break;
                                default:
                                    throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
                            }

                        }
                        outBuffer.append((char) value);
                    } else {
                        if (aChar == 't')
                            aChar = '\t';
                        else if (aChar == 'r')
                            aChar = '\r';
                        else if (aChar == 'n')
                            aChar = '\n';
                        else if (aChar == 'f')
                            aChar = '\f';
                        outBuffer.append(aChar);
                    }
                } else
                    outBuffer.append(aChar);
            }
            return outBuffer.toString();
        }
    }
}

