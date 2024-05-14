import React, { useState, useEffect } from 'react';
import axios from 'axios';
import 'primereact/resources/themes/lara-light-cyan/theme.css';
import 'primeicons/primeicons.css';
import { FilterMatchMode, FilterOperator } from 'primereact/api';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { ProgressBar } from 'primereact/progressbar';
import { InputMask } from 'primereact/inputmask';
import { FloatLabel } from 'primereact/floatlabel';
import { Dropdown } from 'primereact/dropdown';

const CustomerPhoneList = () => {
  const [filters, setFilters] = useState(null);
  const [globalFilterValue, setGlobalFilterValue] = useState('');
  const [customer, setCustomer] = useState([]);
  const [selectedCustomer, setSelectedCustomer] = useState(null);
  const [logic, setLogic] = useState(false);

  useEffect(() => {
    initFilters();
  }, []);

  useEffect(() => {
    axios
        .get('http://localhost:8080/api/phone/customers')
        .then(response => {
          setCustomer(response?.data);
          console.log("Customers : ", customer);
        })
        .catch(error => {
          console.log(error);
        });
  }, [logic]);

  const formatDate = value => {
    return value.toLocaleDateString('en-US', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
    });
  };

  const formatCurrency = value => {
    return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
  };

  const onGlobalFilterChange = e => {
    const value = e.target.value;
    let _filters = { ...filters };

    _filters['global'].value = value;

    setFilters(_filters);
    setGlobalFilterValue(value);
  };

  const initFilters = () => {
    setFilters({
      global: { value: null, matchMode: FilterMatchMode.CONTAINS },
      name: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
      'country.name': { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
      representative: { value: null, matchMode: FilterMatchMode.IN },
      date: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.DATE_IS }] },
      balance: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.EQUALS }] },
      status: { operator: FilterOperator.OR, constraints: [{ value: null, matchMode: FilterMatchMode.EQUALS }] },
      activity: { value: null, matchMode: FilterMatchMode.BETWEEN },
      verified: { value: null, matchMode: FilterMatchMode.EQUALS },
    });
    setGlobalFilterValue('');
  };

  const renderHeader = () => {
    return (
      <div className="flex flex-wrap align-items-center justify-content-between gap-2">
        <InputText value={globalFilterValue} onChange={onGlobalFilterChange} placeholder="Keyword Search" style={{ fontSize: '12px' }} />
      </div>
    );
  };

  const countryBodyTemplate = rowData => {
    return (
      <div className="flex align-items-center gap-2">
        <img
          alt="flag"
          src="https://primefaces.org/cdn/primereact/images/flag/flag_placeholder.png"
          className={`flag flag-${rowData.country.code}`}
          style={{ width: '24px' }}
        />
        <span>{rowData.country.name}</span>
      </div>
    );
  };

  const representativeBodyTemplate = rowData => {
    const representative = rowData.representative;

    return (
      <div className="flex align-items-center gap-2">
        <img alt={representative.name} src={`https://primefaces.org/cdn/primereact/images/avatar/${representative.image}`} width="32" />
        <span>{representative.name}</span>
      </div>
    );
  };

  const dateBodyTemplate = rowData => {
    return formatDate(rowData.date);
  };

  const balanceBodyTemplate = rowData => {
    return formatCurrency(rowData.balance);
  };

  const statusBodyTemplate = rowData => {
    return '';
  };

  const activityBodyTemplate = rowData => {
    return <ProgressBar value={rowData.activity} showValue={false} style={{ height: '6px' }}></ProgressBar>;
  };

  const paginatorLeft = <Button style={{ fontSize: '10px' }} type="button" icon="pi pi-refresh" text />;
  const paginatorRight = <Button type="button" icon="pi pi-download" text />;
  const header = renderHeader();

  return (
    <div className="card">
      <div>
        <FloatLabel>
          <Dropdown
              inputId="dd-city"
              value={selectedCustomer}
              onChange={e => setSelectedCustomer(e.value)}
              options={customer}
              optionLabel="joinName"
              className="w-full"
              style={{ width: 270 }}
            />
        </FloatLabel>
      </div>
      <br/>
      <div>
        <InputMask id="phone" mask="(9999) 999-99-99" placeholder="(0500) 444-44-44" />
      </div>
      <br/>
      <div>
        <DataTable
          paginator
          showGridlines
          rows={5}
          rowsPerPageOptions={[5, 10, 25, 50]}
          tableStyle={{ minWidth: '50rem' }}
          paginatorTemplate="FirstPageLink PrevPageLink CurrentPageReport NextPageLink LastPageLink RowsPerPageDropdown"
          currentPageReportTemplate="{first} to {last} of {totalRecords}"
          paginatorClassName=""
          paginatorLeft={paginatorLeft}
          paginatorRight={paginatorRight}
          //loading={loading}
          dataKey="id"
          filters={filters}
          globalFilterFields={['name', 'country.name', 'representative.name', 'balance', 'status']}
          header={header}
          style={{ fontSize: '12px' }}
          emptyMessage="No customers found."
        >
          <Column field="index" header="Index" />
          <Column field="name" header="First Name" style={{ minWidth: '12rem' }} />
          <Column header="Last Name" filterField="country.name" style={{ minWidth: '12rem' }} body={countryBodyTemplate} />
          <Column header="E-mail" style={{ minWidth: '14rem' }} body={representativeBodyTemplate} />
          <Column header="City" dataType="date" style={{ minWidth: '10rem' }} body={dateBodyTemplate} />
          <Column header="District" dataType="numeric" style={{ minWidth: '10rem' }} body={balanceBodyTemplate} />
          <Column field="status" header="Address" style={{ minWidth: '12rem' }} body={statusBodyTemplate} />
          <Column
            field="activity"
            header="İşlem"
            showFilterMatchModes={false}
            style={{ minWidth: '8rem', textAlign: 'center' }}
            body={activityBodyTemplate}
          />
        </DataTable>
      </div>
    </div>
  );
};

export default CustomerPhoneList;
