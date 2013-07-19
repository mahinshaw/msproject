package GAIL.src.model;

import GAIL.src.controller.ApplicationController;
import GAIL.src.controller.EdgeController;
import GAIL.src.exceptions.InvalidStateException;
import GAIL.src.view.MultiGeneralizationView;

import java.util.List;

public class MultiGeneralizationFactory {
	private static int numMGMs = 0;
	private static EdgeController ec;
	private static List<MultiGeneralizationModel> modelList;
	private static ApplicationController ac;

	public static void setEdgeController(EdgeController ec) {
		MultiGeneralizationFactory.ec = ec;
	}

	public static void setModelList(List<MultiGeneralizationModel> list) {
		modelList = list;
	}

	public static void setApplicationController(ApplicationController applicationController) {
		ac = applicationController;
	}

	private static void checkPreconditions() {
		if(ec == null)
			throw new InvalidStateException("ec must be initialized before use!");
		if(ac == null)
			throw new InvalidStateException("ac must be initialized before use!");
		if(modelList == null)
			throw new InvalidStateException("modelList must be initialized before use!");
	}

	public static MultiGeneralizationModel create(Statement s) {
		checkPreconditions();
		MultiGeneralizationModel m = new MultiGeneralizationModel("MultiStatement " + ++numMGMs, ac);
		new MultiGeneralizationView(m, ec);
		ac.appendToSessionLog("MULTI-GENERALIZATION CREATED: " + m.getID());
		m.addGeneralization(s);
		modelList.add(m);
		return m;
	}

	public static void recycle(MultiGeneralizationModel mgm) {
		checkPreconditions();
		if(!modelList.remove(mgm))
			throw new IllegalArgumentException("The model supplied was not created by this factory!");
		ac.appendToSessionLog("MULTI-GENERALIZATION REMOVED: " + mgm.getID());
	}

    public static void reset() {
        checkPreconditions();
        for(MultiGeneralizationModel mgm : modelList) {
            mgm.reset();
        }
        modelList.clear();
    }
}
