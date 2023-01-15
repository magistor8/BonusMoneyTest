package com.magistor8.bonusmoneytest.view.cards

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magistor8.bonusmoneytest.R
import com.magistor8.bonusmoneytest.data.retrofit.entires.CardsData
import com.magistor8.bonusmoneytest.databinding.CardBinding
import com.magistor8.bonusmoneytest.databinding.LoadingBinding


class CardsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val LOADING = 1
    private val CARD = 2

    private lateinit var itemClickListener: OnListItemClickListener

    private val diffCallback: DiffUtil.ItemCallback<CardsData> = object: DiffUtil.ItemCallback<CardsData>() {

        override fun areItemsTheSame(old: CardsData, new: CardsData): Boolean {
            return  old.company?.companyId == new.company?.companyId
        }

        override fun areContentsTheSame(old: CardsData, new: CardsData): Boolean {
            return  old.company == new.company &&
                    old.customerMarkParameters?.loyaltyLevel == new.customerMarkParameters?.loyaltyLevel &&
                    old.customerMarkParameters?.mark == new.customerMarkParameters?.mark &&
                    old.mobileAppDashboard?.accentColor == new.mobileAppDashboard?.accentColor &&
                    old.mobileAppDashboard?.backgroundColor == new.mobileAppDashboard?.backgroundColor &&
                    old.mobileAppDashboard?.cardBackgroundColor == new.mobileAppDashboard?.cardBackgroundColor &&
                    old.mobileAppDashboard?.companyName == new.mobileAppDashboard?.companyName &&
                    old.mobileAppDashboard?.highlightTextColor == new.mobileAppDashboard?.highlightTextColor &&
                    old.mobileAppDashboard?.mainColor == new.mobileAppDashboard?.mainColor &&
                    old.mobileAppDashboard?.textColor == new.mobileAppDashboard?.textColor &&
                    old.mobileAppDashboard?.logo == new.mobileAppDashboard?.logo
        }
    }

    private val dataList: AsyncListDiffer<CardsData> = AsyncListDiffer(this, diffCallback)

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return when(viewType) {
            LOADING -> CardsAdapterViewHolderLoading(LoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            CARD -> CardsAdapterViewHolder(CardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> CardsAdapterViewHolderLoading(LoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is CardsAdapterViewHolder -> holder.bind(dataList.currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataList.currentList[position].company == null) {
            LOADING
        } else {
            CARD
        }
    }

    override fun getItemCount(): Int {
        return dataList.currentList.size
    }

    fun submitList(nData: List<CardsData>) {
        val data = dataList.currentList
        if (data.isEmpty()) {
            dataList.submitList(nData.map { it.copy() }.filter { it.company != null })
        } else {
            dataList.submitList(data.plus(nData).filter { it.company != null })
        }
    }

    fun loadingLayout(state: Int) {
        when(state) {
            View.VISIBLE -> {
                val data = dataList.currentList
                if (itemCount == 0) {
                    dataList.submitList(data.plus(CardsData(null)))
                } else if (data.last().company != null) {
                    dataList.submitList(data.plus(CardsData(null)))
                }
            }
            View.GONE -> {
                val data : MutableList<CardsData> = dataList.currentList.toMutableList()
                if (data.isNotEmpty()) {
                    data.removeLast()
                    dataList.submitList(data.map { it.copy() })
                    return
                }
                dataList.submitList(emptyList())
            }
        }
    }

    fun setItemClickListener(itemClickListener: OnListItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    private fun buttonPressed(button: String, id: String) {
        itemClickListener.onItemClick(button, id)
    }
    interface OnListItemClickListener {
        fun onItemClick(button: String, id: String)
    }

    inner class CardsAdapterViewHolder(private val binding: CardBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(data: CardsData) {
            if(layoutPosition != RecyclerView.NO_POSITION) {
                binding.cardName.text = data.mobileAppDashboard?.companyName
                Glide.with(binding.root).load(data.mobileAppDashboard?.logo).into(binding.logo)
                binding.points.text = data.customerMarkParameters?.loyaltyLevel?.requiredSum.toString()
                binding.cashback.text = data.customerMarkParameters?.loyaltyLevel?.number.toString() + context.getString(R.string.percent)
                binding.level.text = data.customerMarkParameters?.loyaltyLevel?.name
                setColors(data)
                clickListeners(data)
            }
        }

        private fun setColors(data: CardsData) {
            binding.card.setBackgroundColor(Color.parseColor(data.mobileAppDashboard?.cardBackgroundColor))
            binding.cardName.setTextColor(Color.parseColor(data.mobileAppDashboard?.highlightTextColor))
            binding.points.setTextColor(Color.parseColor(data.mobileAppDashboard?.highlightTextColor))
            binding.pointsText.setTextColor(Color.parseColor(data.mobileAppDashboard?.textColor))
            binding.levelHeader.setTextColor(Color.parseColor(data.mobileAppDashboard?.textColor))
            binding.cashbackHeader.setTextColor(Color.parseColor(data.mobileAppDashboard?.textColor))
            binding.details.setTextColor(Color.parseColor(data.mobileAppDashboard?.mainColor))
            binding.eye.setColorFilter(Color.parseColor(data.mobileAppDashboard?.mainColor))
            binding.trash.setColorFilter(Color.parseColor(data.mobileAppDashboard?.accentColor))
            binding.details.setBackgroundColor(Color.parseColor(data.mobileAppDashboard?.backgroundColor))
        }

        private fun clickListeners(data: CardsData) {
            binding.details.setOnClickListener {
                buttonPressed(
                    context.getString(R.string.details),
                    data.company?.companyId ?: ""
                )
            }
            binding.trash.setOnClickListener {
                buttonPressed(
                    context.getString(R.string.trash),
                    data.company?.companyId ?: ""
                )
            }
            binding.eye.setOnClickListener {
                buttonPressed(
                    context.getString(R.string.eye),
                    data.company?.companyId ?: ""
                )
            }
        }
    }

    inner class CardsAdapterViewHolderLoading(private val binding: LoadingBinding): RecyclerView.ViewHolder(binding.root)


}