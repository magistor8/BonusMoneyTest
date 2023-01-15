package com.magistor8.bonusmoneytest.view.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.magistor8.bonusmoneytest.R
import com.magistor8.bonusmoneytest.data.retrofit.entires.CardsData
import com.magistor8.bonusmoneytest.databinding.FragmentCardBinding
import com.magistor8.bonusmoneytest.domain.contracts.CardFragmentContract
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.getOrCreateScope
import org.koin.core.component.inject
import org.koin.core.scope.Scope
import retrofit2.HttpException


class CardsFragment : Fragment(), KoinScopeComponent {

    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    private var data : MutableList<CardsData> = mutableListOf()

    override val scope: Scope by getOrCreateScope()
    private val adapter : CardsAdapter by inject()
    private val viewModel : CardsFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        adapterClickListener()
        initScrollListener()

        viewModel.viewState.observe(viewLifecycleOwner) { state -> renderData(state) }
    }

    private fun adapterClickListener() {
        adapter.setItemClickListener(object : CardsAdapter.OnListItemClickListener {
            override fun onItemClick(button: String, id: String) {
                showAlert("Нажата кнопка $button у компании $id")
            }
        })
    }

    private fun initScrollListener() {
        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager != null
                    && linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1
                    && adapter.itemCount > 1
                ) {
                    loadMore()
                }
            }
        })
    }

    private fun loadMore() {
        viewModel.onEvent(CardFragmentContract.Events.LoadMoreData)
    }

    //Рендер лайвдаты
    private fun renderData(state: CardFragmentContract.ViewState) {
        when(state) {
            is CardFragmentContract.ViewState.Error -> renderError(state.throwable)
            is CardFragmentContract.ViewState.Loading -> {
                loadingLayout(View.VISIBLE)
            }
            is CardFragmentContract.ViewState.Success -> loadData(state.data)
        }
    }

    private fun renderError(throwable: Throwable) {
        if (throwable is HttpException) {
            when(throwable.response()?.code()) {
                401 -> showAlert(getString(R.string.AuthorizationError))
                400 -> showAlert(throwable.response()?.message() ?: getString(R.string.Error))
                500 -> showAlert(getString(R.string.BadRequest))
                else -> showAlert(getString(R.string.Error))
            }
        } else {
            showAlert(getString(R.string.Error))
        }
    }

    private fun loadData(data: List<CardsData>) {
        loadingLayout(View.GONE)
        adapter.submitList(data)
    }

    private fun showAlert(alert: String) {
        loadingLayout(View.GONE)
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(alert)
        builder.setTitle(getString(R.string.DialogAlertTitle))
        builder.setCancelable(false)
        builder.setPositiveButton(
            R.string.ok
        ) { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun loadingLayout(state: Int) {
        adapter.loadingLayout(state)
    }

    override fun onDetach() {
        scope.close()
        super.onDetach()
    }
}