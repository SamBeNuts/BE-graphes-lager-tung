package org.insa.algo.shortestpath;

import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.AbstractSolution;
import org.insa.graph.Arc;
import org.insa.graph.Path;

public class ShortestPathSolution extends AbstractSolution {

    // Optimal solution.
    private Path path;
    
    // Data from the solution
    private int nb_arcs;
    private int nb_explores;
    private int nb_marques;
    private int max_tas;

    /**
     * {@inheritDoc}
     */
    public ShortestPathSolution(ShortestPathData data) {
        super(data);
    }

    /**
     * Create a new infeasible shortest-path solution for the given input and
     * status.
     * 
     * @param data Original input data for this solution.
     * @param status Status of the solution (UNKNOWN / INFEASIBLE).
     */
    public ShortestPathSolution(ShortestPathData data, Status status) {
        super(data, status);
    }

    /**
     * Create a new shortest-path solution.
     * 
     * @param data Original input data for this solution.
     * @param status Status of the solution (FEASIBLE / OPTIMAL).
     * @param path Path corresponding to the solution.
     */
    public ShortestPathSolution(ShortestPathData data, Status status, Path path) {
        super(data, status);
        this.path = path;
    }

    /**
     * Create a new shortest-path solution.
     * 
     * @param data Original input data for this solution.
     * @param status Status of the solution (FEASIBLE / OPTIMAL).
     * @param path Path corresponding to the solution.
     */
    public ShortestPathSolution(ShortestPathData data, Status status, Path path, int nb_arcs, int nb_explores, int nb_marques, int max_tas) {
        super(data, status);
        this.path = path;
        this.nb_arcs = nb_arcs;
        this.nb_explores = nb_explores;
        this.nb_marques = nb_marques;
        this.max_tas = max_tas;
    }

    @Override
    public ShortestPathData getInputData() {
        return (ShortestPathData) super.getInputData();
    }

    /**
     * @return The path of this solution, if any.
     */
    public Path getPath() {
        return path;
    }
    
    public int getNbArcs() {
    	return nb_arcs;
    }
    
    public int getNbExplores() {
    	return nb_explores;
    }
    
    public int getNbMarques() {
    	return nb_marques;
    }
    
    public int getMaxTas() {
    	return max_tas;
    }

    @Override
    public String toString() {
        String info = null;
        if (!isFeasible()) {
            info = String.format("No path found from node #%d to node #%d",
                    getInputData().getOrigin().getId(), getInputData().getDestination().getId());
        }
        else {
            double cost = 0;
            for (Arc arc: getPath().getArcs()) {
                cost += getInputData().getCost(arc);
            }
            info = String.format("Found a path from node #%d to node #%d",
                    getInputData().getOrigin().getId(), getInputData().getDestination().getId());
            if (getInputData().getMode() == Mode.LENGTH) {
                info = String.format("%s, %.4f kilometers", info, cost / 1000.0);
            }
            else {
                info = String.format("%s, %.4f minutes", info, cost / 60.0);
            }
        }
        info += " in " + getSolvingTime().getSeconds() + " seconds.";
        return info;
    }

}
