package com.iterika.walli.domain

import com.iterika.walli.repos.ScanRepository
import javax.inject.Inject

class ScanInteractor @Inject constructor(val scanRepository: ScanRepository) {

    fun getScanInfo() = scanRepository.getScanInfoAsync()

    fun scanMatches(s : String, passId: String?) = s.equals(passId)
}