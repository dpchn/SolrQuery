import java.util.Scanner;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class FacetQuery {
	public void allFacetQuery(HttpSolrClient client) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("1 : Field faceting");
		System.out.println("2 : Query Facet ");
		System.out.println("2 : Range Facet ");

		System.out.println("Choose option : ");
		int n = scanner.nextInt();
		switch (n) {
		case 1:
			fieldFaceting(client);
			break;
		case 2:
			queryFaceting(client);
			break;
		case 3:
			rangeFaceting(client);
			break;


		}
	}

	void fieldFaceting(HttpSolrClient client) {
		
		SolrQuery query = new SolrQuery();
		query.add("q", "*:*");
		query.add("facet", "true");
		query.add("facet.field", "company");
		try {
			QueryResponse response = client.query(query);
			System.out.println(response.getResponse());
			printResult(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * useful to bring back counts for arbitrary subqueries so you know how many
	 * results might match a future search and provide analytics based upon that
	 * number.
	 */
	void queryFaceting(HttpSolrClient client) {
		SolrQuery query = new SolrQuery();
		query.add("q", "*:*");
		query.add("fq", "price:[500 TO 1500]");
		query.add("fq", "price:[1500 TO 2500]");
		query.add("fq", "price:[2500 TO *]");
		query.add("facet", "true");
		query.add("facet.query", "company:(Onida OR Samsung OR IFB OR LG)");
		query.add("facet.query", "company:(Onida OR Samsung)");
		query.add("facet.query", "company:(Ondia)");
		query.add("facet.query", "comapny:(Samsung)");

		try {

			QueryResponse response = client.query(query);
			printResult(response);
			System.out.println(response.getResponse());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/*
	 * Range faceting, as its name implies, provides the ability to bucketize
	 * numeric and date field values into ranges such that the ranges (and their
	 * counts) get returned from Solr as a facet.
	 */
	void rangeFaceting(HttpSolrClient client) {
		SolrQuery query = new SolrQuery();
		query.add("q", "*:*");
		query.add("facet", "true");
		query.add("facet.range", "price");
		query.add("facet.range.start", "0");
		query.add("facet.range.end", "5000");
		query.add("facet.range.gap", "500");

		try {

			QueryResponse response = client.query(query);
			printResult(response);
			System.out.println(response.getResponse());
		} catch (Exception e) {
			// TODO: handle exception
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
