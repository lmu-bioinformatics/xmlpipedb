package edu.lmu.xmlpipedb.util.engines;

/**
 * This interface is responsible for defining the call back methods that a
 * TallyEngine instance uses.
 * 
 * @author geocoso2
 * 
 */
public interface TallyEngineDelegate {

    /**
     * This method is called when a @see
     * edu.lmu.xmlpipedb.util.engines.Criterion specified with a
     * 
     * @see edu.lmu.xmlpipedb.util.engines.FindBodyRule is parsed and matched.
     *      The instance of this interface will receive the body of the found
     *      node. This allows inline rule addition to the parser.
     * 
     * 
     * @param body
     *            The string that contains the body of a XML node
     */
    public CriterionList processXMLBody(String body);

    /**
     * 
     * 
     * @param column
     *            The entry for the column
     */
    public void processDBColumn(String column);

}