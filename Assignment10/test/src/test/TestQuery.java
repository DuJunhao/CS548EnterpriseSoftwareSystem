package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ModelMaker;

public class TestQuery
{
  public static void main(String[] args)
  {
    try
    {
      InputStream in = new FileInputStream(new File("RDF.xml"));
      Model m = ModelFactory.createMemModelMaker().createModel(null).read(in, null);
      String queryString1 = "PREFIX clinic: <http://example.org/clinic#> PREFIX owl: <http://www.w3.org/2002/07/owl#>PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>SELECT ?Patient WHERE {  ?Patient <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://example.org/clinic#RadiologyPatient> }";

      String queryString2 = "PREFIX clinic: <http://example.org/clinic#> PREFIX owl: <http://www.w3.org/2002/07/owl#>PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>SELECT ?Treatment WHERE {  ?Treatment <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://example.org/clinic#Radiology> }";

      Query query1 = QueryFactory.create(queryString1);
      Query query2 = QueryFactory.create(queryString2);

      QueryExecution qe1 = QueryExecutionFactory.create(query1, m);
      ResultSet results1 = qe1.execSelect();
      ResultSetFormatter.out(System.out, results1);
      qe1.close();
      QueryExecution qe2 = QueryExecutionFactory.create(query2, m);
      ResultSet results2 = qe2.execSelect();
      ResultSetFormatter.out(System.out, results2);
      qe2.close();
    } catch (IOException e) {
      System.out.println(e.toString());
    }
  }
}