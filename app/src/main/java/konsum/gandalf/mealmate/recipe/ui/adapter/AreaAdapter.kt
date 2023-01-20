package konsum.gandalf.mealmate.recipe.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import konsum.gandalf.mealmate.databinding.BtnAreaViewBinding
import konsum.gandalf.mealmate.recipe.data.api.models.AreaResponse

class AreaAdapter(
    private var categorys: List<AreaResponse> = ArrayList<AreaResponse>(),
    private var selectedData: MutableLiveData<MutableList<AreaResponse>>
) : RecyclerView.Adapter<AreaAdapter.ButtonAdapterHolder>() {

    inner class ButtonAdapterHolder(val binding: BtnAreaViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonAdapterHolder {
        val binding = BtnAreaViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ButtonAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: ButtonAdapterHolder, position: Int) {
        with(holder) {
            with(categorys[position]) {
                binding.areaChip.text = name

                binding.areaChip.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedData.value?.add(this)
                    } else {
                        selectedData.value?.remove(this)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return categorys.size
    }
}
