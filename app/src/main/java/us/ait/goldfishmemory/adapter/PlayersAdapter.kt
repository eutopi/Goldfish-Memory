package us.ait.goldfishmemory.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_player.view.*
import us.ait.goldfishmemory.R
import us.ait.goldfishmemory.data.Player

class PlayersAdapter(
    private val context: Context,
    private val uId: String
) : RecyclerView.Adapter<PlayersAdapter.ViewHolder>() {

    private var playerList = mutableListOf<Player>()
    private var playerKeys = mutableListOf<String>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_player, parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount() = playerList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (playerId, username, time, gamesPlayed, icon) = playerList[holder.adapterPosition]
        holder.tvPlayer.text = username
        holder.tvTime.text = time.toString()

        if (icon.isNotEmpty()) {
            holder.ivPhoto.visibility = View.VISIBLE
            Glide.with(context).load(icon).into(holder.ivPhoto)
        }
    }

    fun addPlayer(player: Player, key: String) {
        playerList.add(player)
        playerKeys.add(key)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPlayer: TextView = itemView.tvPlayer
        val tvTime: TextView = itemView.tvTime
        val ivPhoto: ImageView = itemView.ivIcon
    }
}