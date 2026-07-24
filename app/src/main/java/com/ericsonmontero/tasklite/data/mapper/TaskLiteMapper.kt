package com.ericsonmontero.tasklite.data.mapper

import com.ericsonmontero.tasklite.data.models.TaskEntity
import com.ericsonmontero.tasklite.domain.models.TaskDomainModel

fun TaskEntity.toDomainModel(): TaskDomainModel = TaskDomainModel(
    id, title, description, state
)

fun TaskDomainModel.toEntity(): TaskEntity = TaskEntity(
    id, title, description, state
)