package dev.rudyevhenii.edu_material_actualizer.job.mapper;

import dev.rudyevhenii.edu_material_actualizer.job.domain.Job;
import dev.rudyevhenii.edu_material_actualizer.job.model.JobModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface JobModelMapper {

    JobModel map(Job job);

    Job map(JobModel jobModel);
}
