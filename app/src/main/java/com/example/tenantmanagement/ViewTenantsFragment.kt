package com.example.tenantmanagementsystemgroupa

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.tenantmanagement.databinding.FragmentViewTenantsBinding

class ViewTenantsFragment : Fragment() {
    private var _binding: FragmentViewTenantsBinding? = null
    private val binding get() = _binding!!

    // SAFE ARGS: reads the arguments that arrived with the action.
    // ViewTenantsFragmentArgs was generated from the <argument> in nav_graph.xml
    private val args: ViewTenantsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewTenantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // The tenant sent from Add Tenant, or null if we came from the Dashboard
        val tenant = args.tenant

        // DATA BINDING: give it to <variable name="tenant"> in the layout.
        // newTenantTextView shows @{tenant.summary()}, so it updates itself
        binding.tenant = tenant

        // No new tenant? Hide "Just added", the box and the Call button together
        // newTenantGroup ← the <Group> in fragment_view_tenants.xml
        if (tenant == null) {
            binding.newTenantGroup.visibility = View.GONE
        }

        binding.callButton.setOnClickListener {
            if (tenant != null) {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${tenant.phone}"))
                startActivity(intent)
            }
        }

        // Show every saved tenant as text, one after another
        val tenants = TenantRepository.tenants
        if (tenants.isEmpty()) {
            binding.allTenantsTextView.text = "No tenants yet. Add one from the Dashboard."
        } else {
            binding.allTenantsTextView.text = tenants.joinToString("\n\n") { it.summary() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
