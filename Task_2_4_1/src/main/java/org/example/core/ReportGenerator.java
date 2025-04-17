package org.example.core;

import org.example.core.models.LabResult;
import org.example.core.models.GroupSummary;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportGenerator {
    public static String generate(List<LabResult> results) {
        StringBuilder html = new StringBuilder()
                .append("<html><style>table {border-collapse: collapse;} td, th {border: 1px solid black;}</style><body>");

        // Группировка по задачам
        Map<String, List<LabResult>> byTask = results.stream()
                .collect(Collectors.groupingBy(LabResult::getTaskId));

        byTask.forEach((taskId, taskResults) -> {
            html.append("<h3>").append(taskId).append("</h3>")
                    .append("<table><tr><th>Студент</th><th>Сборка</th><th>Документация</th><th>Тесты</th><th>Баллы</th></tr>");

            taskResults.forEach(res -> html.append(
                    String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%d</td></tr>",
                            res.getStudentId(),
                            res.isBuildSuccess() ? "+" : "-",
                            res.getTestResults(),
                            res.getTotalScore()
                    )
            ));
            html.append("</table>");
        });



        html.append("</table></body></html>");
        return html.toString();
    }
}