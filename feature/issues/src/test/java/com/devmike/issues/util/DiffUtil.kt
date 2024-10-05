package com.devmike.issues.util

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.devmike.domain.models.AssigneeModel
import com.devmike.domain.models.IssueModel
import com.devmike.domain.models.LabelModel

// Test utilities
class IssueItemUpdateCallback : ListUpdateCallback {
    val insertEvents = mutableListOf<InsertEvent>()
    val removeEvents = mutableListOf<RemoveEvent>()
    val moveEvents = mutableListOf<MoveEvent>()
    val updateEvents = mutableListOf<UpdateEvent>()

    fun reset() {
        insertEvents.clear()
        removeEvents.clear()
        moveEvents.clear()
        updateEvents.clear()
    }

    override fun onInserted(
        position: Int,
        count: Int,
    ) {
        insertEvents.add(InsertEvent(position, count))
    }

    override fun onRemoved(
        position: Int,
        count: Int,
    ) {
        removeEvents.add(RemoveEvent(position, count))
    }

    override fun onMoved(
        fromPosition: Int,
        toPosition: Int,
    ) {
        moveEvents.add(MoveEvent(fromPosition, toPosition))
    }

    override fun onChanged(
        position: Int,
        count: Int,
        payload: Any?,
    ) {
        updateEvents.add(UpdateEvent(position, count, payload))
    }

    data class InsertEvent(
        val position: Int,
        val count: Int,
    )

    data class RemoveEvent(
        val position: Int,
        val count: Int,
    )

    data class MoveEvent(
        val fromPosition: Int,
        val toPosition: Int,
    )

    data class UpdateEvent(
        val position: Int,
        val count: Int,
        val payload: Any?,
    )
}

class IssueDiffCallback : DiffUtil.ItemCallback<IssueModel>() {
    override fun areItemsTheSame(
        oldItem: IssueModel,
        newItem: IssueModel,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: IssueModel,
        newItem: IssueModel,
    ): Boolean = oldItem == newItem
}

object AssigneeDiffCallback : DiffUtil.ItemCallback<AssigneeModel>() {
    override fun areItemsTheSame(
        oldItem: AssigneeModel,
        newItem: AssigneeModel,
    ): Boolean = oldItem.username == newItem.username

    override fun areContentsTheSame(
        oldItem: AssigneeModel,
        newItem: AssigneeModel,
    ): Boolean = oldItem == newItem
}

object LabelDiffCallback : DiffUtil.ItemCallback<LabelModel>() {
    override fun areItemsTheSame(
        oldItem: LabelModel,
        newItem: LabelModel,
    ): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: LabelModel,
        newItem: LabelModel,
    ): Boolean = oldItem == newItem
}
