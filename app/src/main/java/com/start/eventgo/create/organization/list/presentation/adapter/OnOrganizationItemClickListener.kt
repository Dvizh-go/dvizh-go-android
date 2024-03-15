package com.start.eventgo.create.organization.list.presentation.adapter

import com.start.eventgo.create.organization.list.presentation.model.Organization

interface OnOrganizationItemClickListener {
    fun onItemClick(data: Organization)
}
