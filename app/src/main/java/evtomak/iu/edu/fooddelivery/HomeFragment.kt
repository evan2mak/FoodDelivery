package evtomak.iu.edu.fooddelivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val recentRestaurantsFragment = RecentRestaurantsFragment.newInstance()
        val recentRestaurantsTransaction = childFragmentManager.beginTransaction()
        recentRestaurantsTransaction.replace(
            R.id.recentRestaurantsContainer,
            recentRestaurantsFragment
        )
        recentRestaurantsTransaction.commit()

        val allRestaurantsFragment = AllRestaurantsFragment.newInstance()
        val allRestaurantsTransaction = childFragmentManager.beginTransaction()
        allRestaurantsTransaction.replace(
            R.id.allRestaurantsContainer,
            allRestaurantsFragment
        )
        allRestaurantsTransaction.commit()

        drawerLayout = view.findViewById(R.id.drawerLayout)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            requireActivity(),
            drawerLayout,
            R.string.drawer_open,
            R.string.drawer_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        val openDrawerButton = view.findViewById<Button>(R.id.btnOpenDrawer)
        openDrawerButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val navigationView = view.findViewById<NavigationView>(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_recent_orders -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    findNavController().navigate(R.id.action_homeFragment_to_recentOrdersFragment)
                    true
                }
                R.id.nav_calendar_view -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    findNavController().navigate(R.id.calendarViewFragment)
                    true
                }
                R.id.nav_sign_out -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    FirebaseAuth.getInstance().signOut()
                    findNavController().navigate(R.id.action_homeFragment_to_userScreenFragment)
                    true
                }
                else -> false
            }
        }

        return view
    }
}

