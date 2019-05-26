package org.insa.algo.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.Graph;
import org.insa.graph.Point;
import org.insa.graph.Node;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class AStarTest {
	
	private static ShortestPathSolution validPath, invalidPath;
	private static ShortestPathSolution  emptyPath2_Time_AStar, shortPath2_Time_AStar, longPath2_Time_AStar;	
	private static ShortestPathSolution  emptyPath2_Length_AStar, shortPath2_Length_AStar, longPath2_Length_AStar;
	private static ShortestPathSolution  shortPath2_Time_Bellman, longPath2_Time_Bellman;	
	private static ShortestPathSolution  shortPath2_Length_Bellman, longPath2_Length_Bellman;
    private static Graph map1, map2, map3, map4, map5;
    private static Node n1_2, n2_2, n3_2, n1_3, n2_3, n3_3;
    private static ArcInspector aiTime, aiLength;
    
	
	@BeforeClass
    public static void initAll() throws IOException{
		aiTime = ArcInspectorFactory.getAllFilters().get(0);
		aiLength = ArcInspectorFactory.getAllFilters().get(2);
		
        String map1Name = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre.mapgr";
        String map2Name = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr";
        String map3Name = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/new-zealand.mapgr";
        String map4Name = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre-dense.mapgr";
        String map5Name = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/rhone-alpes.mapgr";
        
        // Create a graph reader.
        GraphReader reader1 = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map1Name))));
        GraphReader reader2 = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map2Name))));
        GraphReader reader3 = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map3Name))));
        GraphReader reader4 = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map4Name))));
        GraphReader reader5 = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map5Name))));
        
        // Read the graph.
        map1 = reader1.read();
        map2 = reader2.read();
        map3 = reader3.read();
        map4 = reader4.read();
        map5 = reader5.read();
                
        n1_2 = map2.get(14852);
        n2_2 = map2.get(14687);
        n3_2 = map2.get(24060);      
        n1_3 = map3.get(40885);
        n2_3 = map3.get(230387);
        n3_3 = map3.get(307194);
        
        validPath = (new AStarAlgorithm(new ShortestPathData(map3, n1_3, n2_3, aiTime))).doRun();
        invalidPath = (new AStarAlgorithm(new ShortestPathData(map3, n1_3, n3_3, aiTime))).doRun();
        
		
		emptyPath2_Time_AStar = (new AStarAlgorithm(new ShortestPathData(map2, n1_2, n1_2, aiTime))).doRun();
		shortPath2_Time_AStar = (new AStarAlgorithm(new ShortestPathData(map2, n1_2, n2_2, aiTime))).doRun();	
		longPath2_Time_AStar = (new AStarAlgorithm(new ShortestPathData(map2, n1_2, n3_2, aiTime))).doRun();	     
		
		emptyPath2_Length_AStar = (new AStarAlgorithm(new ShortestPathData(map2, n1_2, n1_2, aiLength))).doRun();
		shortPath2_Length_AStar = (new AStarAlgorithm(new ShortestPathData(map2, n1_2, n2_2, aiLength))).doRun();	
		longPath2_Length_AStar = (new AStarAlgorithm(new ShortestPathData(map2, n1_2, n3_2, aiLength))).doRun();	
		
		
		shortPath2_Time_Bellman = (new BellmanFordAlgorithm(new ShortestPathData(map2, n1_2, n2_2, aiTime))).doRun();	
		longPath2_Time_Bellman = (new BellmanFordAlgorithm(new ShortestPathData(map2, n1_2, n3_2, aiTime))).doRun();	     

		shortPath2_Length_Bellman = (new BellmanFordAlgorithm(new ShortestPathData(map2, n1_2, n2_2, aiLength))).doRun();	
		longPath2_Length_Bellman = (new BellmanFordAlgorithm(new ShortestPathData(map2, n1_2, n3_2, aiLength))).doRun();
	}

    @Test
    public void testValid() {
        assertTrue(validPath.getPath().isValid());
        assertEquals(invalidPath.getStatus(), Status.INFEASIBLE);
    }

	@Test
    public void testOptimaliteOracle() {
		for (int i =0; i<25;i++) {
			for(int j=i+1;j<25;j++) {
				System.out.println(i + "-" + j);
				ShortestPathSolution path_Length_AStar = (new AStarAlgorithm(new ShortestPathData(map1, map1.get(i), map1.get(j), aiLength))).doRun();
				ShortestPathSolution path_Length_Bellman = (new BellmanFordAlgorithm(new ShortestPathData(map1, map1.get(i), map1.get(j), aiLength))).doRun();
				ShortestPathSolution path_Time_AStar = (new AStarAlgorithm(new ShortestPathData(map1, map1.get(i), map1.get(j), aiTime))).doRun();
				ShortestPathSolution path_Time_Bellman = (new BellmanFordAlgorithm(new ShortestPathData(map1, map1.get(i), map1.get(j), aiTime))).doRun();
				assertEquals(path_Length_AStar.getPath().getLength(), path_Length_Bellman.getPath().getLength(), 1);
				assertEquals(path_Time_AStar.getPath().getMinimumTravelTime(), path_Time_Bellman.getPath().getMinimumTravelTime(), 0.001);
			}
		}
        assertEquals(shortPath2_Time_AStar.getPath().getMinimumTravelTime(), shortPath2_Time_Bellman.getPath().getMinimumTravelTime(), 0.001);
        assertEquals(longPath2_Time_AStar.getPath().getMinimumTravelTime(), longPath2_Time_Bellman.getPath().getMinimumTravelTime(), 0.001);
        assertEquals(shortPath2_Length_AStar.getPath().getLength(), shortPath2_Length_Bellman.getPath().getLength(), 1);
        assertEquals(longPath2_Length_AStar.getPath().getLength(), longPath2_Length_Bellman.getPath().getLength(), 1);
    }

	@Test
    public void testOptimalite() {
		assertEquals(emptyPath2_Time_AStar.getPath().getMinimumTravelTime(), 0, 0.001);
		assertEquals(emptyPath2_Length_AStar.getPath().getLength(), 0, 0.001);
    }
	
	@Test
	public void testPerformance() throws IOException{
		int nb_exec = 200;
		Graph[] maps = {map2, map5};
		BufferedWriter[] bw = {
				new BufferedWriter(new FileWriter("rhone-alpes_distance_" + nb_exec + "_AStar_data.txt")), 
				new BufferedWriter(new FileWriter("rhone-alpes_temps_" + nb_exec + "_AStar_data.txt")),
				new BufferedWriter(new FileWriter("carre-dense_distance_" + nb_exec + "_AStar_data.txt")),
				new BufferedWriter(new FileWriter("carre-dense_temps_" + nb_exec + "_AStar_data.txt"))};
		bw[0].write("rhone-alpes"  + "\n" + 0 + "\n" + nb_exec + "\n");
		bw[1].write("rhone-alpes"  + "\n" + 1 + "\n" + nb_exec + "\n");
		bw[2].write("carre-dense"  + "\n" + 0 + "\n" + nb_exec + "\n");
		bw[3].write("carre-dense"  + "\n" + 1 + "\n" + nb_exec + "\n");
		Random rand = new Random();
		for(int j = 0; j < maps.length; j++) {
			for(int i = 0;i < nb_exec; i++) {
				System.out.println(i+1 + "/" + nb_exec);
				int o_l = rand.nextInt(maps[j].getNodes().size());
				int d_l = rand.nextInt(maps[j].getNodes().size());
				int o_t = rand.nextInt(maps[j].getNodes().size());
				int d_t = rand.nextInt(maps[j].getNodes().size());
				ShortestPathSolution path_Length_AStar = (new AStarAlgorithm(new ShortestPathData(maps[j], maps[j].get(o_l), maps[j].get(d_l), aiLength))).run();
				ShortestPathSolution path_Time_AStar = (new AStarAlgorithm(new ShortestPathData(maps[j], maps[j].get(o_t), maps[j].get(d_t), aiTime))).run();
			    bw[j*2].write(path_Length_AStar.getInputData().getOrigin().toString() + 
			    		"\t" + path_Length_AStar.getInputData().getDestination().toString() +
			    		"\t" + Point.distance(maps[j].get(o_l).getPoint(), maps[j].get(d_l).getPoint()) +
			    		"\t" + path_Length_AStar.getSolvingTime().toNanos() +
			    		"\t" + path_Length_AStar.getNbArcs() + 
			    		"\t" + path_Length_AStar.getNbExplores() + 
			    		"\t" + path_Length_AStar.getNbMarques() +
			    		"\t" + path_Length_AStar.getMaxTas() + "\n");
			    
			    bw[j*2+1].write(path_Time_AStar.getInputData().getOrigin().toString() + 
			    		"\t" + path_Time_AStar.getInputData().getDestination().toString() + 
			    		"\t" + Point.distance(maps[j].get(o_t).getPoint(), maps[j].get(d_t).getPoint()) +
			    		"\t" + path_Time_AStar.getSolvingTime().toNanos() +
			    		"\t" + path_Time_AStar.getNbArcs() + 
			    		"\t" + path_Time_AStar.getNbExplores() + 
			    		"\t" + path_Time_AStar.getNbMarques() +
			    		"\t" + path_Time_AStar.getMaxTas() + "\n");
			}
		}
		bw[0].close();
		bw[1].close();
		bw[2].close();
		bw[3].close();
	}
}
