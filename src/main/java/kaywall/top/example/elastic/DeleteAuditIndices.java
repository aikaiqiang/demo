package kaywall.top.example.elastic;


import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DeleteAuditIndices {

    private static final Logger logger = LoggerFactory.getLogger(DeleteAuditIndices.class);


    public static void main(String[] args) {
        // 设置用户名和密码
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("cosmic", "BcmElastic@2025"));

        // 创建RestClientBuilder并设置认证
        RestClientBuilder builder = RestClient.builder(
                        new HttpHost("172.20.185.76", 9200, "http"))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        RestHighLevelClient client = new RestHighLevelClient(builder);


        try {
            // 获取所有索引
            GetIndexRequest getIndexRequest = new GetIndexRequest("*");
            String[] indices = client.indices().get(getIndexRequest, RequestOptions.DEFAULT).getIndices();

            // 过滤出以 "audit" 开头的索引
            List<String> auditIndices = Arrays.stream(indices)
                    .filter(index -> index.startsWith("audit-") || index.startsWith("bg-") || index.startsWith("bgmd-") || index.startsWith("emr-") || index.startsWith("epm-"))
                    .toList();

            // 删除这些索引
            for (String index : auditIndices) {
                try {
                    DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
                    AcknowledgedResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
                    if (deleteIndexResponse.isAcknowledged()) {
                        logger.info("Index {} deleted successfully.", index);
                    } else {
                        logger.warn("Failed to delete index {}", index);
                    }
                } catch (IOException e) {
                    logger.error("Error deleting index {}: {}", index, e.getMessage(), e);
                }
            }
            logger.info("index count = {}", auditIndices.size());
        } catch (IOException e) {
            logger.error("Error while retrieving or processing indices: {}", e.getMessage(), e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                logger.error("Error closing Elasticsearch client: {}", e.getMessage(), e);
            }
        }
    }
}