import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class SortingInSolr {

	public void sortAsc(HttpSolrClient client) {
		System.out.println("Sorting.....");
		SolrQuery query = new SolrQuery();
		query.add("q","*:*");
		//query.add("sort","name asc");
		//query.add("sort","name asc, price desc");
		query.add("sort","name asc,price asc");
		try {
			QueryResponse response = client.query(query);
			printResult(response);
		} catch (Exception e) {
			
		}
		
	}
	
	void printResult(QueryResponse response) {
		SolrDocumentList list = response.getResults();
		for (SolrDocument doc : list) {
			
			for (String str : doc.getFieldNames()) {
				System.out.println(str + " : " + doc.getFieldValue(str));
			}

			System.out.println();
		}
		return;
	}
}
