package io.dfjinxin.common.utils.python;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc: 调用远程python Api 方法
 * @Param: 接口地址，jsonStr
 * @Return: 根据接口不同返回 str或jsonStr
 * @Author: z.h.c
 * @Date: 2019/11/1 17:29
 */
public class PythonApiUtils {

    private static Logger logger = LoggerFactory.getLogger(PythonApiUtils.class);

    public static String doPost(String url, String jsonStr) {

        HttpURLConnection conn = null;
        String result = null;
        try {
            conn = getConn(url);
            conn.connect();

            //发送数据
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(jsonStr);
            out.flush();
            out.close();

            int code = conn.getResponseCode();
            InputStream is;
            if (code == 200) {
                logger.info("连接成功");
                is = conn.getInputStream();
            } else {
                logger.info("连接失败：" + code);
                return null;
            }

            //读取数据
            int length = conn.getContentLength();// 获取长度
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[1024];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                result = new String(data, "UTF-8"); // utf-8编码
                logger.info("调用python-{}服务，结果:{}", url, result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("调用python-{}服务，异常:{}", url, e.getMessage());
        }
        return result;
    }

    private static HttpURLConnection getConn(final String urlStr) throws IOException {
        //创建连接
        URL url = new URL(urlStr);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        try {
            connection.setRequestMethod("POST");                                // 设置请求方式
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        connection.setRequestProperty("Accept", "application/json");        // 设置接收数据的格式
        connection.setRequestProperty("Content-Type", "application/json");  // 设置发送数据的格式
        return connection;
    }


    public static void main(String[] args) {

        String[] valArr = new String[]{
                "b_1",
                "b_2",
                "m_3",
                "m_2"};
        Map<String, Object> params = new HashMap<>();
        params.put("table", "pig_data");
        params.put("variable", valArr);
        String json = JSON.toJSONString(params);
        String res = doPost("CorAna", json);
        System.out.println("结果：" + res);


        /*String json2 = "{\"indeVar\":[39,13]}";
        String res2 = doPost("createDataSet", json2);
        System.out.println("结果：" + res2);*/
    }
}
