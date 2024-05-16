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

const CustomerPhoneList = (props) => {
  const [filters, setFilters] = useState(null);
  const [globalFilterValue, setGlobalFilterValue] = useState('');
  const [customer, setCustomer] = useState([]);
  const [selectedCustomer, setSelectedCustomer] = useState(null);
  const [logic, setLogic] = useState(false);
  const [number, setNumber] = useState();
  const [data, setData] = useState();

  useEffect(() => {
    initFilters();
  }, []);

  useEffect(() => {
    axios
        .get('http://localhost:8080/api/phone/phones')
        .then(response => {
          setData(response.data);
          console.log('Table:', response.data);
        })
        .catch(error => {
          console.log(error);
        });
  }, [logic]);

  useEffect(() => {
    if (props.param === true) {
      axios
        .get('http://localhost:8080/api/phone/customers')
        .then(response => {
          setCustomer(response?.data);
          console.log('Customers : ', customer);
        })
        .catch(error => {
          console.log(error);
        });
    }
  }, [logic]);

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



  



  const activityBodyTemplate = rowData => {
    return <ProgressBar value={rowData.activity} showValue={false} style={{ height: '6px' }}></ProgressBar>;
  };

  const paginatorLeft = <Button style={{ fontSize: '10px' }} type="button" icon="pi pi-refresh" text />;
  const paginatorRight = <Button type="button" icon="pi pi-download" text />;
  const header = renderHeader();

  const savePhone = () => {
    console.log('Telefon Number : ', number);
    const values = {
      phoneNumber: number,
      customerDTO: {
        id: selectedCustomer?.id,
      },
    };
    axios
      .post('http://localhost:8080/api/phone', values)
      .then(response => {
        console.log('Yeni Phone Veri: ', response.data);
      })
      .catch(error => {
        console.log(error);
      });
    setLogic(!logic);
    //window.location.reload();
  };

  return (
    <div className="card">
      <div>
        <FloatLabel>
          <Dropdown
            inputId="dd-customer"
            value={selectedCustomer}
            onChange={e => setSelectedCustomer(e.value)}
            options={customer}
            optionLabel="joinName"
            className="w-full md:w-14rem"
            filter
            style={{ width: 270 }}
          />
          <label htmlFor="dd-customer">Select a Customer</label>
        </FloatLabel>
      </div>
      <br />
      <div>
        <InputMask
          id="phone"
          mask="(9999) 999-99-99"
          placeholder="(0500) 444-44-44"
          value={number}
          onChange={e => setNumber(e.target.value)}
        />
      </div>
      <br />
      <div>
        <Button label="Save Phone" icon="pi pi-save" style={{ width: 200 }} onClick={savePhone} />
      </div>
      <br />
      <br />
      <div>
        <DataTable
          value={data}
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
          <Column header="Index" body={(data, options) => options.rowIndex + 1} style={{ fontSize: '12px', textAlign: 'center' }} />
          <Column field="customerDTO.firstName" header="First Name" style={{ minWidth: '12rem' }} />
          <Column field="customerDTO.lastName" header="Last Name" style={{ minWidth: '12rem' }} />
          <Column field="customerDTO.email" header="E-mail" style={{ minWidth: '14rem' }}  />
          <Column header="City" style={{ minWidth: '10rem' }}  />
          <Column header="District" style={{ minWidth: '10rem' }} />
          <Column field="status" header="Address" style={{ minWidth: '12rem' }} />
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
