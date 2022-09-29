package com.example.prodan

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.prodan.databinding.FragmentUserBinding
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =FragmentUserBinding.inflate(inflater, container, false)
        setVarChart()

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextBtn.visibility = View.GONE
        binding.editBtn.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_editProfileFragment)
        }
    }
    private fun setVarChart() {

        val preferences = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val r1 = preferences?.getFloat("C1", 0f)
        val r2 = preferences?.getFloat("C2", 0f)
        val r3 = preferences?.getFloat("C3", 0f)
        val r4 = preferences?.getFloat("C4", 0f)
        val r5 = preferences?.getFloat("C5", 0f)
        val name =preferences?.getString("PetName", "")
        val description = preferences?.getString("Description", "")

        if(name != "" && description != ""){
            binding.PetName.text = name
            binding.PetDescription.text = description
        }



        val xvalues = ArrayList<String>()
        xvalues.apply{
            add("Bueno con niños")
            add("Sociable")
            add("Energético")
            add("Amigable")
            add("Travieso")
        }



        val entries: MutableList<RadarEntry> = ArrayList()
        r1?.let { RadarEntry(it, "Bueno con niños") }?.let { entries.add(it) }
        r2?.let { RadarEntry(it, "Sociable") }?.let { entries.add(it) }
        r3?.let { RadarEntry(it, "Energético") }?.let { entries.add(it) }
        r4?.let { RadarEntry(it, "Amigable") }?.let { entries.add(it) }
        r5?.let { RadarEntry(it, "Travieso") }?.let { entries.add(it) }

        val set = RadarDataSet(entries, "RadarDataSet")
        var colorList = mutableListOf<Int>()
        ColorTemplate.JOYFUL_COLORS.forEach {colorList.add(it)  }



        // var colorList = mutableListOf<Int>(ColorTemplate.JOYFUL_COLORS.forEach {  })
        set.color = Color.rgb(10, 120, 180)
        set.fillColor = Color.rgb(52, 173, 207);
        set.setDrawFilled(true);
        set.fillAlpha = 180;
        set.lineWidth = 2f;
        set.isDrawHighlightCircleEnabled = true;
        set.setDrawHighlightIndicators(false);
        // set.setColors(ColorTemplate.COLORFUL_COLORS)

        var data = RadarData(set)
        binding.apply {
            radarChart.data = data
            radarChart.animateY(2000)
            radarChart.description.isEnabled = false
            radarChart.legend.isEnabled = false
            radarChart.description.text = "Datos"
            radarChart.xAxis.valueFormatter = (myValueFormater(xvalues))
            radarChart.yAxis.axisMinimum = 0f
            radarChart.yAxis.axisMaximum = 5f
            radarChart.yAxis.isEnabled = false
        }

            //   binding.migrafica.invalidate()



    }

}
class myValueFormater(private val xvalues : ArrayList<String>) : ValueFormatter(){
    override fun getFormattedValue(value: Float): String {
        return value.toString()
    }

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        if (value.toInt() >= 0 && value.toInt() <= xvalues.size - 1) {
            return xvalues[value.toInt()]
        } else {
            return ("").toString()
        }
    }

}
