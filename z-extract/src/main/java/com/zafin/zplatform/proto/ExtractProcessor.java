package com.zafin.zplatform.proto;

import java.util.List;
import java.util.Map;

/**
 * The ExtractorProcessor works with Change Sets. An ExtractorProcessor will
 * only get a payload from an 'exportable' change set. A change set is deemed
 * 'exportable' if all of its target systems acknowledge that changeset can be
 * accepted.
 * 
 * @author Paul Goddard
 *
 */
public class ExtractProcessor {

    public PayLoad getPayLoad(ChangeSet changeSet,List<TargetSystem> targetSystems) throws BuilderServiceException {
        /*
         * Surprise: ExportableChangeSet is already a Payload
         */
        return getExportableChangeSet(changeSet, targetSystems);
    }

    private ExportableChangeSet getExportableChangeSet(ChangeSet changeSet, List<TargetSystem> targetSystems) throws BuilderServiceException {
        return new TestChangeSet(targetSystems, changeSet);
    }

    static class TestChangeSet extends ExportableChangeSet {

        public TestChangeSet(List<TargetSystem> validTargetSystems, ChangeSet changeSet) throws BuilderServiceException {
            super(validTargetSystems, changeSet);
        }

        @Override
        public void load() {
            /*
             * This is where the mapping magic should happen.
             * Get the properties from the held changeset.
             */
            for (Map.Entry<String, Object> entry : getAllProperties().entrySet()) {
                // For testing This just regurgitates all values provided in the internal changeset.
                // This is a valid hack since this TestChangeSet is an 'ExportableChangeSet'... 
                // which means it has been validated.
                // This wholesale load will transfer the held changeset values to its own internal property set.
                put(entry.getKey(), entry.getValue());
            }
        }
    }

}
