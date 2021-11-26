package com.sandboxygn.yangoncv.model

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CvViewModel : ViewModel() {

    //Step 1
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _dateOfBirth = MutableLiveData<String>()
    val dateOfBirth: LiveData<String> = _dateOfBirth

    private val _fatherName = MutableLiveData<String>()
    val fatherName: LiveData<String> = _fatherName



    //Step 2
    private val _race = MutableLiveData<String>()
    val race: LiveData<String> = _race

    private val _nationality = MutableLiveData<String>()
    val nationality: LiveData<String> = _nationality

    private val _nrcNo = MutableLiveData<String>()
    val nrcNo: LiveData<String> = _nrcNo

    //Step 3
    private val _religion = MutableLiveData<String>()
    val religion: LiveData<String> = _religion

    private val _sex = MutableLiveData<String>()
    val sex: LiveData<String> = _sex

    private val _martialStatus = MutableLiveData<String>()
    val martialStatus: LiveData<String> = _martialStatus

    private val _weight = MutableLiveData<String>()
    val weight: LiveData<String> = _weight


    private val _height = MutableLiveData<String>()
    val height: LiveData<String> = _height

    //Step 4
    var skilledLangs = mutableListOf<String>()  // is Myanmar checked , English checked? etc..
    var skilledLangsEdited = mutableListOf<String>()  //Skill level added to checked lang
    var skilledLangsString = skilledLangs.joinToString()  //combine list into one long string

    //Step 5

    private val _educationalQualification = MutableLiveData<String>()
    val educationalQualification: LiveData<String> = _educationalQualification

    private val _certificates = MutableLiveData<String>()
    val certificates : LiveData<String> = _certificates

    //Step 6
    private val _expectedSalary = MutableLiveData<String>()
    val expectedSalary: LiveData<String> = _expectedSalary

    private val _contactAddress = MutableLiveData<String>()
    val contactAddress: LiveData<String> = _contactAddress

    private val _phoneNo = MutableLiveData<String>()
    val phoneNo: LiveData<String> = _phoneNo

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    //Step 7

    private val _workExp = MutableLiveData<String>()
    val workExp: LiveData<String> = _workExp

    private val _profileImage = MutableLiveData<Uri?>()
    val profileImage : LiveData<Uri?> =_profileImage

    private val _isVisible = MutableLiveData<Boolean>()
    val isVisible : LiveData<Boolean> = _isVisible


    init{
        resetData()
    }

    fun resetData(){
        _name.value = ""
        _dateOfBirth.value=""
        _fatherName.value = ""
        _race.value = ""
        _nationality.value = ""
        _nrcNo.value = ""
        _religion.value = ""
        _sex.value = ""
        _martialStatus.value = ""
        _weight.value = ""
        _height.value = ""
        skilledLangs = mutableListOf<String>()
        skilledLangsEdited= mutableListOf<String>()
        _educationalQualification.value=""
        _certificates.value = ""
        _expectedSalary.value = ""
        _contactAddress.value = ""
        _phoneNo.value = ""
        _email.value = ""
        _workExp.value = ""
        _profileImage.value = null

    }

    //Step 1 property setters
    fun setName(name : String){
        _name.value = name
    }
    fun setDateOfBirth(dateOfBirth : String ){
        _dateOfBirth.value = dateOfBirth
    }
    fun setFatherName(fatherName : String){
        _fatherName.value = fatherName
    }

    //Step 2 property setters
    fun setRace(race : String){
        _race.value = race
    }
    fun setNationality(nationality : String){
        _nationality.value = nationality
    }
    fun setNrcNo(nrcNo : String){
        _nrcNo.value = nrcNo
    }

    //Step 3 property setters
    fun setReligion(religion : String){
        _religion.value = religion
    }
    fun setSex(sex : String){
        _sex.value = sex
    }
    fun setMartialStatus(martialStatus : String){
        _martialStatus.value = martialStatus
    }
    fun setWeight(weight : String){
        _weight.value = weight
    }
    fun setHeight(height : String){
        _height.value = height
    }

    //Step 4 property setters



    //Step 5 property setters
    fun setEducationalQualification(quilification : String){
        _educationalQualification.value = quilification
    }
    fun setCertificates(certs : String){
        _certificates.value = certs
    }

    //Step 6 property setters
    fun setExpectedSalary(salary : String){
        _expectedSalary.value = salary
    }
    fun setAddress(address : String){
        _contactAddress.value = address
    }
    fun setPhoneNo(phoneNo : String){
        _phoneNo.value = phoneNo
    }
    fun setEmail(email : String){
        _email.value = email
    }

    //Step 7 property setters
    fun setWorkExp(experience : String){
        _workExp.value = experience
    }

    fun setProfileImage(uri: Uri?){
        _profileImage.value = uri
    }

    fun setIsVisible(boolean: Boolean){
        _isVisible.value = boolean
    }





}