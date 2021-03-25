package com.example.mymemory

import android.content.Context
import android.content.res.ColorStateList
import android.service.quickaccesswallet.GetWalletCardsCallback
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mymemory.models.BoardSize
import com.example.mymemory.models.MemoryCard
import kotlin.math.min

class MemoryBoardAdapter(
        private val context: Context,
        private val boardSize: BoardSize,
        private val cards: List<MemoryCard>,
        private val cardClickListener: MemoryBoardAdapter.CardClickListener
        ) :
        RecyclerView.Adapter<MemoryBoardAdapter.ViewHolder>() {

      companion object{
          private const val MARGIN_SIZE=10
          private const val TAG="MemoryBoardAdapter"
      }
    interface CardClickListener{
        fun onCardClicked(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardwidth = parent.width/boardSize.getwidth()-(2* MARGIN_SIZE)
        val cardHeight = parent.height/boardSize.getHeight()-(2* MARGIN_SIZE)
        val cardSideLength= min(cardwidth,cardHeight)
        val view = LayoutInflater.from(context).inflate(R.layout.memory_card,parent,false)
        val layoutParams=view.findViewById<CardView>(R.id.cardview).layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width=cardSideLength
        layoutParams.height=cardSideLength
        layoutParams.setMargins(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount()= boardSize.numCards
    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        private val ImageButton=itemView.findViewById<ImageButton>(R.id.imageButton)
        fun bind(position: Int) {
            val memoryCard = cards[position]
            ImageButton.setImageResource(if (memoryCard.isFaceUp) memoryCard.identifier else R.drawable.image)

           ImageButton.alpha= if (memoryCard.isMatched) .4f else 1.0f
            val ColorStateList=if (memoryCard.isMatched)ContextCompat.getColorStateList(context,R.color.color_gray)else null
            ViewCompat.setBackgroundTintList(ImageButton,ColorStateList)


            ImageButton.setOnClickListener {
               Log.i(TAG,"click on position $position")
                cardClickListener.onCardClicked(position)
           }
           }
        }
    }

