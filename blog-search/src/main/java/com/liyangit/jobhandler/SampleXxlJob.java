package com.liyangit.jobhandler;

import com.liyangit.constant.RabbitMQConstant;
import com.liyangit.entity.ArticleEsEntity;
import com.liyangit.entity.QueueConsumptionFailureEntity;
import com.liyangit.entity.QueueSendFailureEntity;
import com.liyangit.mapper.ArticleEsMapper;
import com.liyangit.mapper.QueueConsumptionFailureMapper;
import com.liyangit.mapper.QueueSendFailureMapper;
import com.liyangit.repository.ArticleEsRepository;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * XxlJob开发示例（Bean模式）
 * <p>
 * 开发步骤：<br>
 * 1、任务开发：在Spring Bean实例中，开发Job方法；<br>
 * 2、注解配置：为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。<br>
 * 3、执行日志：需要通过 "XxlJobHelper.log" 打印执行日志；<br>
 * 4、任务结果：默认任务结果为 "成功" 状态，不需要主动设置；如有诉求，比如设置任务结果为失败，可以通过 "XxlJobHelper.handleFail/handleSuccess" 自主设置任务结果；<br>
 *
 * @author xuxueli 2019-12-11 21:52:51
 */
@Component
@SuppressWarnings("all")
public class SampleXxlJob {
    private static final Logger logger = LoggerFactory.getLogger(SampleXxlJob.class);

    private final ArticleEsRepository repository;
    private final ElasticsearchRestTemplate template;
    private final QueueConsumptionFailureMapper consumptionFailureMapper;
    private final QueueSendFailureMapper sendFailureMapper;
    private final ArticleEsMapper mapper;

    public SampleXxlJob(ArticleEsRepository repository,
                        ElasticsearchRestTemplate template,
                        QueueConsumptionFailureMapper consumptionFailureMapper,
                        QueueSendFailureMapper sendFailureMapper,
                        ArticleEsMapper mapper) {
        this.repository = repository;
        this.template = template;
        this.consumptionFailureMapper = consumptionFailureMapper;
        this.sendFailureMapper = sendFailureMapper;
        this.mapper = mapper;
    }

    /**
     * 更新发送失败的ES同步文章记录
     */
    @XxlJob("esFailedToRetrySendingArticle")
    public void esFailedToRetrySendingArticle() {
        List<QueueSendFailureEntity> list = sendFailureMapper.querySendFailureList(RabbitMQConstant.SYNC_ARTICLE__TO_ES_ROUTER_KEY.getValue());
        LocalDateTime now = LocalDateTime.now();
        int saveCount = 0;
        for (QueueSendFailureEntity entity : list) {
            try {
                entity.setStatus(true);
                entity.setUpdatedTime(now);
                // 保存数据库，放在前面 避免出现 es保存成功 保存数据库失败
                sendFailureMapper.updateById(entity);
                String message = entity.getMessage();
                if (message.contains("\"controls\":\"insert\"") || message.contains("\"controls\":\"update\"")) {
                    // 保存或者修改
                    ArticleEsEntity article = mapper.getArticleById(entity.getMessageId());
                    if (article != null) {
                        repository.save(article);
                    }
                } else {
                    // 删除
                    repository.deleteById(entity.getMessageId());
                }
                saveCount++;
            } catch (Exception e) {
                logger.error("定时任务执行 [同步文章失败消息] 保存ES数据失败，原因：", e.getMessage());
            }
        }


        if (list.size() > 0 && saveCount == 0) {
            XxlJobHelper.handleFail("执行更新发送失败的ES同步文章记录失败，原因没有一条数据能正常同步");
        } else {
            XxlJobHelper.handleSuccess(String.format("执行更新发送失败的ES同步文章记录 此次更新条数 %s", saveCount));
        }
    }


    /**
     * 更新重试失败的ES同步文章记录
     */
    @XxlJob("esRetryingSynchronizationArticle")
    public void esRetryingSynchronizationArticle() {
        List<QueueConsumptionFailureEntity> list = consumptionFailureMapper.queryConsumptionFailureList(RabbitMQConstant.SYNC_ARTICLE__TO_ES_ROUTER_KEY.getValue());
        XxlJobHelper.handleFail("测试 失败结果");
    }

    /**
     * 扫描最近三天更新的文章数据，将数据同步到ES
     */
    @XxlJob("esModifyUpdateArticle")
    public void esModifyUpdateArticle() {

    }

    /**
     * 扫描最近三天新增的文章数据，将数据同步到ES
     */
    @XxlJob("esModifyInsertArticle")
    public void esModifyInsertArticle() {

    }


    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler")
    public void demoJobHandler() throws Exception {
        XxlJobHelper.log("XXL-JOB, Hello World.");

        for (int i = 0; i < 5; i++) {
            XxlJobHelper.log("beat at:" + i);
            TimeUnit.SECONDS.sleep(2);
        }
        // default success
    }


    /**
     * 2、分片广播任务
     */
    @XxlJob("shardingJobHandler")
    public void shardingJobHandler() throws Exception {

        // 分片参数
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();

        XxlJobHelper.log("分片参数：当前分片序号 = {}, 总分片数 = {}", shardIndex, shardTotal);

        // 业务逻辑
        for (int i = 0; i < shardTotal; i++) {
            if (i == shardIndex) {
                XxlJobHelper.log("第 {} 片, 命中分片开始处理", i);
            } else {
                XxlJobHelper.log("第 {} 片, 忽略", i);
            }
        }

    }


    /**
     * 3、命令行任务
     */
    @XxlJob("commandJobHandler")
    public void commandJobHandler() throws Exception {
        String command = XxlJobHelper.getJobParam();
        int exitValue = -1;

        BufferedReader bufferedReader = null;
        try {
            // command process
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(command);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            //Process process = Runtime.getRuntime().exec(command);

            BufferedInputStream bufferedInputStream = new BufferedInputStream(process.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            // command log
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                XxlJobHelper.log(line);
            }

            // command exit
            process.waitFor();
            exitValue = process.exitValue();
        } catch (Exception e) {
            XxlJobHelper.log(e);
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        if (exitValue == 0) {
            // default success
        } else {
            XxlJobHelper.handleFail("command exit value(" + exitValue + ") is failed");
        }

    }


    /**
     * 4、跨平台Http任务
     * 参数示例：
     * "url: http://www.baidu.com\n" +
     * "method: get\n" +
     * "data: content\n";
     */
    @XxlJob("httpJobHandler")
    public void httpJobHandler() throws Exception {

        // param parse
        String param = XxlJobHelper.getJobParam();
        if (param == null || param.trim().length() == 0) {
            XxlJobHelper.log("param[" + param + "] invalid.");

            XxlJobHelper.handleFail();
            return;
        }

        String[] httpParams = param.split("\n");
        String url = null;
        String method = null;
        String data = null;
        for (String httpParam : httpParams) {
            if (httpParam.startsWith("url:")) {
                url = httpParam.substring(httpParam.indexOf("url:") + 4).trim();
            }
            if (httpParam.startsWith("method:")) {
                method = httpParam.substring(httpParam.indexOf("method:") + 7).trim().toUpperCase();
            }
            if (httpParam.startsWith("data:")) {
                data = httpParam.substring(httpParam.indexOf("data:") + 5).trim();
            }
        }

        // param valid
        if (url == null || url.trim().length() == 0) {
            XxlJobHelper.log("url[" + url + "] invalid.");

            XxlJobHelper.handleFail();
            return;
        }
        if (method == null || !Arrays.asList("GET", "POST").contains(method)) {
            XxlJobHelper.log("method[" + method + "] invalid.");

            XxlJobHelper.handleFail();
            return;
        }
        boolean isPostMethod = method.equals("POST");

        // request
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        try {
            // connection
            URL realUrl = new URL(url);
            connection = (HttpURLConnection) realUrl.openConnection();

            // connection setting
            connection.setRequestMethod(method);
            connection.setDoOutput(isPostMethod);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setReadTimeout(5 * 1000);
            connection.setConnectTimeout(3 * 1000);
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Accept-Charset", "application/json;charset=UTF-8");

            // do connection
            connection.connect();

            // data
            if (isPostMethod && data != null && data.trim().length() > 0) {
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
                dataOutputStream.flush();
                dataOutputStream.close();
            }

            // valid StatusCode
            int statusCode = connection.getResponseCode();
            if (statusCode != 200) {
                throw new RuntimeException("Http Request StatusCode(" + statusCode + ") Invalid.");
            }

            // result
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            String responseMsg = result.toString();

            XxlJobHelper.log(responseMsg);

        } catch (Exception e) {
            XxlJobHelper.log(e);

            XxlJobHelper.handleFail();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e2) {
                XxlJobHelper.log(e2);
            }
        }

    }

    /**
     * 5、生命周期任务示例：任务初始化与销毁时，支持自定义相关逻辑；
     */
    @XxlJob(value = "demoJobHandler2", init = "init", destroy = "destroy")
    public void demoJobHandler2() throws Exception {
        XxlJobHelper.log("XXL-JOB, Hello World.");
    }

    public void init() {
        logger.info("init");
    }

    public void destroy() {
        logger.info("destroy");
    }


}
