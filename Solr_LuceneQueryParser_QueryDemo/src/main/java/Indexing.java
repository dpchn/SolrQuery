
import java.io.IOException;

import org.apache.jute.Index;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.common.params.ModifiableSolrParams;

public class Indexing {
	public void FullImport(HttpSolrClient client) {
		ModifiableSolrParams params = new ModifiableSolrParams();

		try {

			params.set("qt", "/dataimport");
			params.set("command", "full-import");

			client.query(params);

			System.out.println("Succesfully full import.....");
			client.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void DeltaImport() {
		SolrServer startServer = new SolrServer();
		HttpSolrClient client = startServer.StartSolrServer();
		ModifiableSolrParams params = new ModifiableSolrParams();
		System.out.println("Deleta import....");
		params.set("qt", "/dataimport");
		params.set("command", "delta-import");
		try {
			client.query(params);
			System.out.println("Delta import Done....");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
