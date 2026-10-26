package dev.rudyevhenii.edu_material_actualizer.systemone.validator;

import dev.rudyevhenii.edu_material_actualizer.document.domain.AggregatedBlock;
import dev.rudyevhenii.edu_material_actualizer.systemone.constants.QuestionKey;
import dev.rudyevhenii.edu_material_actualizer.systemone.integration.dto.Answer;
import dev.rudyevhenii.edu_material_actualizer.systemone.integration.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SystemOneThresholdValidator {

    @Value("${app.system-one.response.threshold}")
    private double threshold;

    public void verifyThreshold(AggregatedBlock aggregatedBlock, Response response) {
        Map<String, Answer> answers = response.answers();
        Answer answer = answers.get(QuestionKey.REQUIRES_ACTUALIZATION.getValue());
        Answer.Noul noul = (Answer.Noul) answer;

        if (noul.noul() >= threshold) {
            aggregatedBlock.setRequiredActualization(true);
        }
    }
}
