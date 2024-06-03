package com.applicationtls.tools.data.local.database.reminder

import com.applicationtls.tools.data.local.database.EntityMapper
import com.applicationtls.tools.ui.reminder.domain.ReminderModel

object ReminderEntityMapper:EntityMapper<ReminderModel,ReminderEntity> {

    override fun asEntity(domain: ReminderModel): ReminderEntity {
        return ReminderEntity(
            id = domain.id,
            content = domain.content,
            time = domain.time,
            isDone = domain.isDone
        )
    }

    override fun asDomain(entity: ReminderEntity): ReminderModel {
        return ReminderModel(
            id = entity.id,
            content = entity.content,
            time = entity.time,
            isDone = entity.isDone
        )
    }

}

fun ReminderModel.asEntity(): ReminderEntity {
    return ReminderEntityMapper.asEntity(this)
}

fun ReminderEntity.asDomain(): ReminderModel {
    return ReminderEntityMapper.asDomain(this)
}