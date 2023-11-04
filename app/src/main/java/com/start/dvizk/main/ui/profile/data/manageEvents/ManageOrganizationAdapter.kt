
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.create.organization.list.presentation.model.Organization
import com.start.dvizk.databinding.ItemManageOrganizationBinding

class ManageOrganizationAdapter() : RecyclerView.Adapter<ManageOrganizationAdapter.ManageOrganizationHolder>() {

    private var organizationList = listOf<Organization>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageOrganizationHolder {
        val itemBinding = ItemManageOrganizationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ManageOrganizationHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ManageOrganizationHolder, position: Int) {
        val paymentBean: Organization = organizationList[position]
        holder.bind(paymentBean)
    }

    override fun getItemCount(): Int = organizationList.size

    class ManageOrganizationHolder(private val itemBinding: ItemManageOrganizationBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(organization: Organization) {
            itemBinding.itemOrganizationSubtitleTextView.text = organization.description
            itemBinding.itemOrganizationTitleTextView.text = organization.name
        }
    }

    fun setData(list: List<Organization>){
        organizationList = list
        notifyDataSetChanged()
    }
}