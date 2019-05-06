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
	private static ShortestPathSolution emptyPath1_Time, shortPath1_Time, longPath1_Time, emptyPath2_Time, shortPath2_Time, longPath2_Time;	
	private static ShortestPathSolution emptyPath1_Length, shortPath1_Length, longPath1_Length, emptyPath2_Length, shortPath2_Length, longPath2_Length;	
    private static Graph map1, map2, map3;
    private static String map1Name, map2Name, map3Name;
    private static Node n1_1, n2_1, n3_1, n1_2, n2_2, n3_2, n1_3, n2_3, n3_3;
    private static ArcInspector aiTime, aiLength;
    
	
	@BeforeClass
    public static void initAll() throws IOException{
		aiTime = ArcInspectorFactory.getAllFilters().get(1);
		aiLength = ArcInspectorFactory.getAllFilters().get(3);
		
        map1Name = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre-dense.mapgr";
        map2Name = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/haute-garonne.mapgr";
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
        n1_2 = map2.get(38672);
        n2_2 = map2.get(74382);
        n3_2 = map2.get(32590);      
        n1_3 = map3.get(40885);
        n2_3 = map3.get(230387);
        n3_3 = map3.get(307194);
        
        validPath = (new DijkstraAlgorithm(new ShortestPathData(map3, n1_3, n2_3, aiTime))).doRun();
        invalidPath = (new DijkstraAlgorithm(new ShortestPathData(map3, n1_3, n3_3, aiTime))).doRun();
		/*emptyPath1_Time = (new DijkstraAlgorithm(new ShortestPathData(map1, n1_1, n1_1, aiTime))).doRun();
		shortPath1_Time = (new DijkstraAlgorithm(new ShortestPathData(map1, n1_1, n2_1, aiTime))).doRun();	
		longPath1_Time = (new DijkstraAlgorithm(new ShortestPathData(map1, n1_1, n3_1, aiTime))).doRun();	
		invalidPath1_Time = (new DijkstraAlgorithm(new ShortestPathData(map1, n1_1, n3_1, aiTime))).doRun();	
		emptyPath2_Time = (new DijkstraAlgorithm(new ShortestPathData(map2, n1_2, n1_2, aiTime))).doRun();
		shortPath2_Time = (new DijkstraAlgorithm(new ShortestPathData(map2, n1_2, n2_2, aiTime))).doRun();	
		longPath2_Time = (new DijkstraAlgorithm(new ShortestPathData(map2, n1_2, n3_2, aiTime))).doRun();	
		invalidPath2_Time = (new DijkstraAlgorithm(new ShortestPathData(map1, n1_1, n3_1, aiTime))).doRun();        
		emptyPath1_Length = (new DijkstraAlgorithm(new ShortestPathData(map1, n1_1, n1_1, aiTime))).doRun();
		shortPath1_Length = (new DijkstraAlgorithm(new ShortestPathData(map1, n1_1, n2_1, aiTime))).doRun();	
		longPath1_Length = (new DijkstraAlgorithm(new ShortestPathData(map1, n1_1, n3_1, aiTime))).doRun();	
		invalidPath1_Length = (new DijkstraAlgorithm(new ShortestPathData(map1, n1_1, n3_1, aiTime))).doRun();
		emptyPath2_Length = (new DijkstraAlgorithm(new ShortestPathData(map2, n1_2, n1_2, aiTime))).doRun();
		shortPath2_Length = (new DijkstraAlgorithm(new ShortestPathData(map2, n1_2, n2_2, aiTime))).doRun();	
		longPath2_Length = (new DijkstraAlgorithm(new ShortestPathData(map2, n1_2, n3_2, aiTime))).doRun();	
		invalidPath2_Length = (new DijkstraAlgorithm(new ShortestPathData(map1, n1_1, n3_1, aiTime))).doRun();	*/
	}

    @Test
    public void testValid() {
        assertTrue(validPath.getPath().isValid());
        assertFalse(invalidPath.getPath().isValid());
    }
}
