import java.io.IOException;
import java.util.Scanner;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;

public class TypesOfQuery {
	public void QueryOption(HttpSolrClient client) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("1: Gett All result");
		System.out.println("2: Cost associated Query");
		System.out.println("3: Optional Terms Query");
		System.out.println("4: Grouped Query");
		System.out.println("5: Range Query");
		System.out.println("6: TermProximity Query");
		System.out.println("7: CharacterProximity Query");
		System.out.println("8: WildCard Query");
		System.out.println("9: Boosting Query");
		
		System.out.println("Enter your choice : ");
		int num = scanner.nextInt();

		switch (num) {
		case 1:
			getAllResult(client);
			break;
		case 2:
			QueryCostAssociated(client);
			break;
		case 3:
			optionalTermsQuery(client);
			break;
		case 4:
			groupedExpressionQuery(client);
			break;
		case 5:
			RangeQuery(client);
			break;
		case 6:
			termProximityQuery(client);
			break;
		case 7:
			characterProximityQuery(client);
			break;
		case 8:
			wildCardQuery(client);
			break;
		case 9:
			boostingExpressionQuery(client);
			break;
		}
		return;
	}

	void getAllResult(HttpSolrClient client) {
		SolrQuery query = new SolrQuery("*:*");
		try {
			QueryResponse response = client.query(query);
			printResult(response);
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		return;
	}

	//////////////////////// QueryCostAssociated////////////////////////////////////
	public void QueryCostAssociated(HttpSolrClient client) {
		SolrQuery query = new SolrQuery();

		query.add("q", "tv");
		query.add("fq", "{!cost=1}category:*");
		query.add("fq", "{!cost=2}price:[500 TO 5000]");
		query.setFields("name", "category", "price", "company");

		try {
			QueryResponse response = client.query(query);
			printResult(response);
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		return;
	}

	//////////////////// optionalTermsQuery////////////////////////////////////
	void optionalTermsQuery(HttpSolrClient client) {
		SolrQuery query = new SolrQuery();
		// query.add("q", "+tv+plasma");
		// query.add("q", "-tv -plasma");
		// query.add("q", "tv || plasma");
		// query.add("q", "tv && plasma");
		query.add("q", "tv || LG");
		// query.add("q", "tv OR plasma");
		query.add("fl", "*,score");
		query.setFields("name", "category", "price", "company", "score");
		try {
			QueryResponse response = client.query(query);
			System.out.println(response);
			printResult(response);
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		return;
	}

	////////////////// groupedExpressionQuery/////////////////////
	void groupedExpressionQuery(HttpSolrClient client) {
		SolrQuery query = new SolrQuery();
		query.add("q", "lg AND freez");
		// query.add("q","lg && freez"); not working
		try {
			QueryResponse response = client.query(query);
			printResult(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	////////////// RangeQuery////////////////////////
	void RangeQuery(HttpSolrClient client) {
		SolrQuery query = new SolrQuery();
		query.add("q", "lg");
		query.add("fq", "price:[1000 TO 10000]");
		query.add("fl", "*,score");
		try {
			QueryResponse response = client.query(query);
			printResult(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//////////////// termProximityQuery//////////////////////
	void termProximityQuery(HttpSolrClient client) {
		SolrQuery query = new SolrQuery();
		query.add("q", "'" + "lg" + "'~8");
		try {
			QueryResponse response = client.query(query);
			printResult(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	////////////////// characterProximityQuery////////////////////
	void characterProximityQuery(HttpSolrClient client) {
		SolrQuery query = new SolrQuery();
		query.add("q", "lg~2");
		try {
			QueryResponse response = client.query(query);
			printResult(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	////////////////// wildCardQuery////////////////////
	void wildCardQuery(HttpSolrClient client) {
		SolrQuery query = new SolrQuery();
		query.add("q", "L?G && Freez");
		//query.add("q", "L*G");
		//query.add("q", "freez*");
		try {
			QueryResponse response = client.query(query);
			printResult(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	////////////////// boostingExpressionQuery////////////////////
	void boostingExpressionQuery(HttpSolrClient client) {
		SolrQuery query = new SolrQuery();
		query.add("fl", "*,score");
		query.add("q", "LG Onida^100");
		//query.add("q", "L*G");
		//query.add("q", "freez*");
		try {
			QueryResponse response = client.query(query);
			printResult(response);
		} catch (Exception e) {
			e.printStackTrace();
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
