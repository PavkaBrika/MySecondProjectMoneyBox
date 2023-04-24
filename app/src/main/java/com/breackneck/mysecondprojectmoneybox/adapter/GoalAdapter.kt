package com.breackneck.mysecondprojectmoneybox.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.breackneck.mysecondprojectmoneybox.R
import com.breckneck.mysecondprojectmoneybox.domain.model.GoalDomain
import java.text.DecimalFormat

class GoalAdapter(
    private val goalList: List<GoalDomain>,
    private val goalClickListener: OnGoalClickListener
) : RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    private val decimalFormat = DecimalFormat("#.##")

    interface OnGoalClickListener {
        fun onGoalClick(goalDomain: GoalDomain, position: Int)
    }

    class GoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val goalNameTextView: TextView = itemView.findViewById(R.id.goalNameTextView)
        val goalMoneyTextView: TextView = itemView.findViewById(R.id.goalMoneyTextView)
        val goalLeftTextView: TextView = itemView.findViewById(R.id.goalCost)
        val goalProgressBar: ProgressBar = itemView.findViewById(R.id.goalProgressBar)
        val leftText = itemView.resources.getText(R.string.left)
        val congratulationsText = itemView.resources.getText(R.string.congratulations)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.goals_item, parent, false)
        return GoalViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = goalList[position]
        holder.goalNameTextView.text = goal.item
        holder.goalMoneyTextView.text = decimalFormat.format(goal.money)
        val left = goal.cost - goal.money
        if (goal.cost >= goal.money)
            holder.goalLeftTextView.text = "${decimalFormat.format(left)} ${holder.leftText}"
        else
            holder.goalLeftTextView.text = "${holder.congratulationsText}"
        holder.goalProgressBar.progress = ((100 * goal.money) / goal.cost).toInt()
        holder.itemView.setOnClickListener {
            goalClickListener.onGoalClick(goalDomain = goal, position = position)
        }
    }

    override fun getItemCount(): Int {
        return goalList.size
    }

}