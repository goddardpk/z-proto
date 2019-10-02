package com.zafin.zplatform.proto;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An exportable changeset is derived from a prior changeset.
 * This internal state of the original (validChangeSet) should not be tampered with.
 * This ExportableChangeSet extends Payload so it can be used with the z-api to create objects
 * but the PayLoad.load() method must called to transfer the state over from validChangeSet to this instance.
 * @author root
 *
 */
public abstract class ExportableChangeSet extends ChangeSet {
    private final List<TargetSystem> targetSystems;
    private final ChangeSet validChangeSet;

    public ExportableChangeSet(List<TargetSystem> validTargetSystems, ChangeSet changeSet) throws BuilderServiceException {
        super(changeSet);
        Map<TargetSystem, List<String>> allIssues = new HashMap<>();
        for (TargetSystem targetSystem : validTargetSystems) {
            List<String> issues = targetSystem.reasonForNotAccepting(changeSet);
            if (issues.isEmpty()) {
                //System.out.println("TargetSystem: " + targetSystem.getName() + " happy with " + changeSet);
            } else {
                allIssues.put(targetSystem, issues);
            }
        }
        this.targetSystems = validTargetSystems;
        if (allIssues.isEmpty()) {
            validChangeSet = changeSet;
        } else {
            validChangeSet = null;
        }
    }

    public List<TargetSystem> getTargetSystems() {
        return Collections.unmodifiableList(targetSystems);
    }

    @Override
    public Map<String, Object> getAllProperties() {
        if (validChangeSet == null) {
            return Collections.emptyMap();
        }
        return validChangeSet.getAllProperties();
    }

    public Object get(String key) {
        return validChangeSet.get(key);
    }
    
    @Override
    public String toString() {
        return "Exportable ChangeSet: [" + super.toString() + "]";
    }
}
