// GroupSummary.java
package org.example.core.models;

import java.util.Map;

public class GroupSummary {
    private String groupName;
    private Map<String, StudentStats> studentStats;

    public String getGroupName() { return groupName; }
    public Map<String, StudentStats> getStudentStats() { return studentStats; }
}