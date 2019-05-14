package org.insa.algo.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.Graph;
import org.insa.graph.Path;
import org.insa.graph.Node;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class DisjktraTest {
	
	private static ShortestPathSolution validPath, invalidPath;
	private static ShortestPathSolution emptyPath1_Time_Disjktra, shortPath1_Time_Disjktra, longPath1_Time_Disjktra, emptyPath2_Time_Disjktra, shortPath2_Time_Disjktra, longPath2_Time_Disjktra;	
	private static ShortestPathSolution emptyPath1_Length_Disjktra, shortPath1_Length_Disjktra, longPath1_Length_Disjktra, emptyPath2_Length_Disjktra, shortPath2_Length_Disjktra, longPath2_Length_Disjktra;
	private static ShortestPathSolution shortPath1_Time_Bellman, longPath1_Time_Bellman, shortPath2_Time_Bellman, longPath2_Time_Bellman;	
	private static ShortestPathSolution shortPath1_Length_Bellman, longPath1_Length_Bellman, shortPath2_Length_Bellman, longPath2_Length_Bellman;
    private static Graph map1, map2, map3;
    private static String map1Name, map2Name, map3Name;
    private static Node n1_1, n2_1, n3_1, n1_2, n2_2, n3_2, n1_3, n2_3, n3_3;
    private static ArcInspector aiTime, aiLength;
    
	
	@BeforeClass
    public static void initAll() throws IOException{
		aiTime = ArcInspectorFactory.getAllFilters().get(1);
		aiLength = ArcInspectorFactory.getAllFilters().get(3);
		
        map1Name = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre-dense.mapgr";
        map2Name = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr";
        map3Name = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/new-zealand.mapgr";
        // Create a graph reader.
        GraphReader reader1 = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map1Name))));
        GraphReader reader2 = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map2Name))));
        GraphReader reader3 = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map3Name))));
        // Read the graph.
        Graph map1 = reader1.read();
        Graph map2 = reader2.read();
        Graph map3 = reader3.read();
        
        n1_1 = map1.get(130734);
        n2_1 = map1.get(111950);
        n3_1 = map1.get(143814);        
        n1_2 = map2.get(14852);
        n2_2 = map2.get(14687);
        n3_2 = map2.get(24060);      
        n1_3 = map3.get(40885);
        n2_3 = map3.get(230387);
        n3_3 = map3.get(307194);
        
        validPath = (new DijkstraAlgorithm(new ShortestPathData(map3, n1_3, n2_3, aiTime))).doRun();
        invalidPath = (new DijkstraAlgorithm(new ShortestPathData(map3, n1_3, n3_3, aiTime))).doRun();
        
		/*emptyPath1_Time_Disjktra = (new DijkstraAlgorithm(new ShortestPathData(map1, n1_1, n1_1, aiTime))).doRun();
		shortPath1_Time_Disjktra = (new DijkstraAlgorithm(new ShortestPathData(map1, n1_1, n2_1, aiTime))).doRun();	
		longPath1_Time_Disjktra = (new DijkstraAlgorithm(new ShortestPathData(map1, n1_1, n3_1, aiTime))).doRun();*/	
		emptyPath2_Time_Disjktra = (new DijkstraAlgorithm(new ShortestPathData(map2, n1_2, n1_2, aiTime))).doRun();
		shortPath2_Time_Disjktra = (new DijkstraAlgorithm(new ShortestPathData(map2, n1_2, n2_2, aiTime))).doRun();	
		longPath2_Time_Disjktra = (new DijkstraAlgorithm(new ShortestPathData(map2, n1_2, n3_2, aiTime))).doRun();	     
		/*emptyPath1_Length_Disjktra = (new DijkstraAlgorithm(new ShortestPathData(map1, n1_1, n1_1, aiTime))).doRun();
		shortPath1_Length_Disjktra = (new DijkstraAlgorithm(new ShortestPathData(map1, n1_1, n2_1, aiTime))).doRun();	
		longPath1_Length_Disjktra = (new DijkstraAlgorithm(new ShortestPathData(map1, n1_1, n3_1, aiTime))).doRun();*/	
		emptyPath2_Length_Disjktra = (new DijkstraAlgorithm(new ShortestPathData(map2, n1_2, n1_2, aiTime))).doRun();
		shortPath2_Length_Disjktra = (new DijkstraAlgorithm(new ShortestPathData(map2, n1_2, n2_2, aiTime))).doRun();	
		longPath2_Length_Disjktra = (new DijkstraAlgorithm(new ShortestPathData(map2, n1_2, n3_2, aiTime))).doRun();	
		
		/*shortPath1_Time_Bellman = (new BellmanFordAlgorithm(new ShortestPathData(map1, n1_1, n2_1, aiTime))).doRun();	
		longPath1_Time_Bellman = (new BellmanFordAlgorithm(new ShortestPathData(map1, n1_1, n3_1, aiTime))).doRun();*/	
		shortPath2_Time_Bellman = (new BellmanFordAlgorithm(new ShortestPathData(map2, n1_2, n2_2, aiTime))).doRun();	
		longPath2_Time_Bellman = (new BellmanFordAlgorithm(new ShortestPathData(map2, n1_2, n3_2, aiTime))).doRun();	     
		/*shortPath1_Length_Bellman = (new BellmanFordAlgorithm(new ShortestPathData(map1, n1_1, n2_1, aiTime))).doRun();	
		longPath1_Length_Bellman = (new BellmanFordAlgorithm(new ShortestPathData(map1, n1_1, n3_1, aiTime))).doRun();*/
		shortPath2_Length_Bellman = (new BellmanFordAlgorithm(new ShortestPathData(map2, n1_2, n2_2, aiTime))).doRun();	
		longPath2_Length_Bellman = (new BellmanFordAlgorithm(new ShortestPathData(map2, n1_2, n3_2, aiTime))).doRun();
	}

    @Test
    public void testValid() {
        assertTrue(validPath.getPath().isValid());
        assertEquals(invalidPath.getStatus(), Status.INFEASIBLE);
    }

	@Test
    public void testOptimaliteOracle() {
        assertEquals(shortPath2_Time_Disjktra.getPath().getMinimumTravelTime(), shortPath2_Time_Bellman.getPath().getMinimumTravelTime(), 0.001);
        assertEquals(longPath2_Time_Disjktra.getPath().getMinimumTravelTime(), longPath2_Time_Bellman.getPath().getMinimumTravelTime(), 0.001);
        assertEquals(shortPath2_Length_Disjktra.getPath().getLength(), shortPath2_Length_Bellman.getPath().getLength(), 0.001);
        assertEquals(longPath2_Length_Disjktra.getPath().getLength(), longPath2_Length_Bellman.getPath().getLength(), 0.001);
    }

	@Test
    public void testOptimalite() {
		assertEquals(emptyPath2_Time_Disjktra.getPath().getMinimumTravelTime(), 0, 0.001);
		assertEquals(emptyPath2_Length_Disjktra.getPath().getLength(), 0, 0.001);
    }
}
