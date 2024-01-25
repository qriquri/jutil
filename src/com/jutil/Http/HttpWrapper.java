package com.jutil.Http;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.apache.commons.io.IOUtils;

import com.jutil.Logger.Logger;

public class HttpWrapper {
    private static final String LOG_TAG = HttpWrapper.class.getName();
    private static String EOL = "\r\n"; // <= サーバーのosの改行コードに合わせる

    final public static void setEOL(String eol) {
        EOL = eol;
    }

    /**
     * ファイル転送
     * @param filename
     * @param url
     * @return
     * @throws IOException
     */
    final public static String uploadFile(String filename, String url) throws IOException {
        HttpURLConnection con = null;
        FileInputStream file = null;
        InputStream inputStream = null;
        String response = "{\"success\": false}";
        try {
            // <httpリクエスト設定>
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            sendFileHttp(con, filename, file);
            // </httpリクエスト設定>
            if (con.getResponseCode() == 200) {
            	inputStream = con.getInputStream();
                response = readFromStream(inputStream);
            }

        } catch (Exception e) {
            Logger.error(LOG_TAG, "uploadFile" + e.toString());
        } finally {
            if (con != null) {
            con.disconnect();
            }
            if(file != null){
                file.close();
            }
            if(inputStream != null) {
            	inputStream.close();
            }
        }
        return response;
    }

    /**
     * JSON形式で送信したいときに使う
     *
     * @param body
     * @param url
     * @return
     * @throws IOException
     */
    final public static String sendJSON(String body, String url) throws IOException {
        String response = "{\"success\": false}";
        HttpURLConnection con = null;
        InputStream inputStream = null;
        con = (HttpURLConnection) new URL(url).openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        try {
            OutputStream out = con.getOutputStream();
            out.write((body)
                    .getBytes(StandardCharsets.UTF_8));

            out.flush();

            if (con.getResponseCode() == 200) {
                inputStream = con.getInputStream();
                response = readFromStream(inputStream);
            }
        } catch (Exception e) {
            Logger.error(LOG_TAG, "sendJson" + e.toString());
        } finally {
            if (con != null) {
                con.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return response;
    }

    /**
     * GETリクエストを送り、レスポンスを得る
     *
     * @param url
     * @return
     * @throws IOException
     */
    final public static String createGetReq(String url) throws IOException {
        HttpURLConnection con = null;
        InputStream inputStream = null;
        String response = "{\"success\": false}";
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(1000000);
            con.setConnectTimeout(1500000);
            con.connect();
            if (con.getResponseCode() == 200) {
                // 成功したとき, レスポンスを読み取る
                inputStream = con.getInputStream();
                response = readFromStream(inputStream);
            }
        } catch (Exception e) {
            Logger.error(LOG_TAG, "createGetReq" + e.toString());
        } finally {
            if (con != null) {
                con.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return response;
    }

    /*
     * GETリクエストを送り、レスポンスを得る
     *
     * @param url
     *
     * @return
     *
     * @throws IOException
     */
    final public static InputStream createGetReqGetStream(String url) throws IOException {
        HttpURLConnection con = null;
        InputStream inputStream = null;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(1000000);
            con.setConnectTimeout(1500000);
            con.connect();
            if (con.getResponseCode() == 200) {
                // 成功したとき, レスポンスを読み取る
                inputStream = con.getInputStream();
                Logger.info(LOG_TAG, "create connection!");
                return inputStream;
            }
        } catch (Exception e) {
            Logger.error(LOG_TAG, "createGetReq" + e.toString());
        } finally {

        }
        return inputStream;
    }

    /**
     * JSON形式で送信したいときに使う
     *
     * @param body
     * @param url
     * @return
     * @throws IOException
     */
    final public static InputStream sendJSONGetStream(String body, String url) throws IOException {
        HttpURLConnection con = null;
        InputStream inputStream = null;
        con = (HttpURLConnection) new URL(url).openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setReadTimeout(1000000);
        con.setConnectTimeout(1500000);
        try {
            OutputStream out = con.getOutputStream();
            out.write((body)
                    .getBytes(StandardCharsets.UTF_8));

            out.flush();

            if (con.getResponseCode() == 200) {
                inputStream = con.getInputStream();
                Logger.info(LOG_TAG, "create connection!");
                return inputStream;
            }
        } catch (Exception e) {
            Logger.error(LOG_TAG, "sendJson" + e.toString());
        } finally {
        }
        return inputStream;
    }

    /**
     * GETリクエストを送り、レスポンスを得る
     *
     * @param url
     * @return
     * @throws IOException
     */
    final public static byte[] createGetReqByByte(String url) throws IOException {
        HttpURLConnection con = null;
        InputStream inputStream = null;
        byte[] response = null;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(10000);
            con.setConnectTimeout(15000);
            con.connect();
            if (con.getResponseCode() == 200) {
                // 成功したとき, レスポンスを読み取る
                inputStream = con.getInputStream();
                response = readFromStreamByByte(inputStream);
            }
        } catch (Exception e) {
            Logger.error(LOG_TAG, "createGetReq" + e.toString());
        } finally {
            if (con != null) {
                con.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return response;
    }

    /**
     * httpリクエストの返信を読み取る
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    final private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                if (output.length() == 0) {
                    output.append(line);

                } else {
                    output.append("\n" + line);

                }
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * httpリクエストの返信を読み取る
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    final private static byte[] readFromStreamByByte(InputStream inputStream) throws IOException {
        byte[] output = null;
        if (inputStream != null) {
            // バイト配列に変換する
            output = IOUtils.toByteArray(inputStream);
        }
        return output;
    }

    /**
     * httpリクエストでファイルを送り付ける
     *
     * @param con
     * @param filename
     * @param file
     * @throws IOException
     */
    final public static void sendFileHttp(HttpURLConnection con, String filename, FileInputStream file)
            throws IOException {
        // <httpリクエスト設定>
        final String boundary = UUID.randomUUID().toString();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        // </httpリクエスト設定>
        // <送信>
        OutputStream out = con.getOutputStream();
        out.write(("--" + boundary + EOL +
                "Content-Disposition: form-data; name=\"file\"; " +
                "filename=\"" + filename + "\"" + EOL + EOL)
                .getBytes(StandardCharsets.UTF_8));
        // <ファイルの中身を追加>
        file = new FileInputStream(filename);
        byte[] buffer = new byte[128];
        int size = -1;
        while (-1 != (size = file.read(buffer))) {
            out.write(buffer, 0, size);
        }
        // </ファイルの中身を追加>
        out.write((EOL + "--" + boundary + "--" + EOL).getBytes(StandardCharsets.UTF_8));
        out.flush();
        file.close();
        // </送信>
    }
}
