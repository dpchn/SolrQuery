
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.params.ModifiableSolrParams;

public class SolrServer {
	public HttpSolrClient StartSolrServer() {
		String url = "http://localhost:8983/solr/Query_Demo/";
		HttpSolrClient client = new HttpSolrClient.Builder(url).build();
		client.setParser(new XMLResponseParser());
		return client;
	}
}
