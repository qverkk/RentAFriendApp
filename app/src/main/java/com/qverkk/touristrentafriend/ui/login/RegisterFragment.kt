package com.qverkk.touristrentafriend.ui.login


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.qverkk.touristrentafriend.R
import com.qverkk.touristrentafriend.helpers.RegistrationHelper
import kotlinx.android.synthetic.main.fragment_register.*
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import java.util.stream.Collectors

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {

    private val CITY_NAME_LENGTH_REQUIREMENT = 2

    private val FIRST_NAME_MINIMUM_LENGTH = 2
    private val LAST_NAME_MINIMUM_LENGTH = 2
    private val PRICE_REGEX = Pattern.compile("\\d+(\\.\\d{2})?")
    private val EMAIL_REGEX = Pattern.compile("\\w+@\\w+\\.\\w+")
    private val NUMBER_REGEX = Pattern.compile("\\d")
    private val UPPERCASE_REGEX = Pattern.compile("[A-Z]")
    private val SPECIAL_REGEX = Pattern.compile("[^A-Za-z0-9]")
    private val DESCRIPTION_MINIMUM_LENGTH = 40
    private lateinit var LIST_OF_COUNTRIES: List<String>

    private val myCalendar = Calendar.getInstance()

    private lateinit var navController: NavController

    private val date = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, month)
        myCalendar.set(Calendar.DAY_OF_MONTH, day)
        updateDateLabel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initializeListeners()
    }

    private fun initializeListeners() {
        initializeCountries()
        initializeBirthdayPopup()
        initializeRegisterButton()
        initializePriceChanges()
    }

    private fun initializePriceChanges() {
        editText_register_price.setOnKeyListener { _, _, _ -> if (priceRequirement()) false else false }
    }

    private fun initializeRegisterButton() {
        btn_register_register.setOnClickListener {
            if (usernameRequirement() &&
                passwordRequirement() &&
                passwordMatchRequirement() &&
                firstNameRequirement() &&
                lastNameRequirement() &&
                countryRequirement() &&
                cityNameRequirement() &&
                priceRequirement() &&
                descriptionRequirement() &&
                ageRequirement()
            ) {
                println("Should register")
                proceedRegistration()
            } else {
                println("Errors in registration")
            }
        }
    }

    private fun passwordMatchRequirement(): Boolean {
        if (editText_register_password.text.toString() == editText_register_repeat_password.text.toString()) {
            return true
        }
        editText_register_repeat_password.error = "Passwords must match!"
        return false
    }

    private fun passwordRequirement(): Boolean {
        val password = editText_register_password.text.toString()
        if (!password.contains(NUMBER_REGEX.toRegex()) || !password.contains(UPPERCASE_REGEX.toRegex()) || !password.contains(
                SPECIAL_REGEX.toRegex()
            )
        ) {
            editText_register_password.error =
                "Password must contain at least 1 number, 1 UPPERCASE character, 1 special character"
            return false
        }
        return true
    }

    private fun usernameRequirement(): Boolean {
        return if (editText_register_username.text.toString().matches(EMAIL_REGEX.toRegex())) {
            true
        } else {
            editText_register_username.error = "This isn't a valid email"
            false
        }
    }

    private fun cityNameRequirement(): Boolean {
        val cityName = editText_register_cityName.text.toString()
        return if (cityName.isEmpty() || cityName.length < CITY_NAME_LENGTH_REQUIREMENT) {
            editText_register_cityName.error = "City name is too short"
            false
        } else {
            true
        }
    }

    private fun initializeCountries() {
        val locales = Locale.getAvailableLocales()
        LIST_OF_COUNTRIES =
            locales.toList()
                .stream()
                .map { it.getDisplayCountry(Locale.ENGLISH) }
                .filter { it.isNotEmpty() }
                .sorted()
                .distinct()
                .collect(Collectors.toList())

        val adapter = ArrayAdapter<String>(
            context!!.applicationContext,
            android.R.layout.simple_spinner_item, LIST_OF_COUNTRIES
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_register_country.setAdapter(adapter)
    }

    private fun initializeBirthdayPopup() {
        editText_register_birthdate.setOnClickListener {
            println("Show date picker")
            DatePickerDialog(
                context!!,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateDateLabel() {
        val dateFormat = "MM/dd/yyyy"
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.US)

        editText_register_birthdate.setText(simpleDateFormat.format(myCalendar.time))
        ageRequirement()
    }

    private fun firstNameRequirement(): Boolean {
        val text = editText_register_firstName.text.toString()
        return if (text.isEmpty() || text.length < FIRST_NAME_MINIMUM_LENGTH) {
            editText_register_firstName.error = "First name is too short"
            false
        } else if (text.contains("\\d+")) {
            editText_register_firstName.error = "First name can't contain numbers"
            false
        } else {
            true
        }
    }

    private fun lastNameRequirement(): Boolean {
        val text = editText_register_lastName.text.toString()
        return if (text.isEmpty() || text.length < FIRST_NAME_MINIMUM_LENGTH) {
            editText_register_lastName.error = "Last name is too short"
            false
        } else if (text.contains("\\d+")) {
            editText_register_lastName.error = "Last name can't contain numbers"
            false
        } else {
            true
        }
    }

    private fun countryRequirement(): Boolean {
        return if (LIST_OF_COUNTRIES.contains(spinner_register_country.text.toString())) {
            true
        } else {
            spinner_register_country.error = "This country doesn't match with our database"
            false
        }
    }

    private fun priceRequirement(): Boolean {
        return if (editText_register_price.text.matches(PRICE_REGEX.toRegex())) {
            true
        } else {
            editText_register_price.error = "The price format is: XXXX.XX"
            false
        }
    }

    private fun descriptionRequirement(): Boolean {
        return if (editText_register_description.text.length >= DESCRIPTION_MINIMUM_LENGTH) {
            true
        } else {
            editText_register_description.error = "The description requirement is 40 characters"
            false
        }
    }

    private fun ageRequirement(): Boolean {
        return when {
            editText_register_birthdate.text.isEmpty() -> {
                editText_register_birthdate.error = "Please pick your birthday"
                Toast.makeText(context, "Please pick your birthday", Toast.LENGTH_LONG).show()
                false
            }
            Calendar.getInstance().get(Calendar.YEAR) - myCalendar.get(Calendar.YEAR) >= 18 -> {
                true
            }
            else -> {
                editText_register_birthdate.error = "You must be older than 18 in order to register"
                Toast.makeText(
                    context,
                    "You must be older than 18 in order to register",
                    Toast.LENGTH_LONG
                ).show()
                false
            }
        }
    }

    private fun proceedRegistration() {
        RegistrationHelper(
            context!!,
            navController,
            activity,
            editText_register_firstName.text.toString(),
            editText_register_lastName.text.toString(),
            editText_register_birthdate.text.toString(),
            editText_register_username.text.toString(),
            editText_register_password.text.toString(),
            editText_register_description.text.toString(),
            editText_register_price.text.toString().toBigDecimal(),
            spinner_register_country.text.toString(),
            editText_register_cityName.text.toString()
        ).register()
    }
}

fun <T> getInformationFromErrorResponse(response: Response<T>): String {
    if (response.errorBody() == null) {
        return "No response"
    }
    val br = BufferedReader(InputStreamReader(response.errorBody()!!.byteStream()))
    val sb = StringBuilder()
    br.readLines().forEach {
        sb.append(it)
    }
    br.close()
    return sb.toString()
}
