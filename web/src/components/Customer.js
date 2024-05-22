import React, { useState, useEffect, useRef, useCallback } from 'react';
import { Toast } from 'primereact/toast';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { Dropdown } from 'primereact/dropdown';
import { FloatLabel } from 'primereact/floatlabel';
import { InputTextarea } from 'primereact/inputtextarea';
import CustomerService from '../services/CustomerService';
import '../styles/Customer.css';

export const Customer = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [address, setAddress] = useState('');
  const [selectedCity, setSelectedCity] = useState(null);
  const [selectedDistrict, setSelectedDistrict] = useState(null);
  const [cities, setCities] = useState([]);
  const [districts, setDistricts] = useState([]);
  const [filters, setFilters] = useState({ global: { value: null, matchMode: 'contains' } });
  const [data, setData] = useState([]);
  const [globalFilterValue, setGlobalFilterValue] = useState('');
  const [logic, setLogic] = useState(false);
  const toast = useRef(null);

  const fetchCustomers = useCallback(() => {
    CustomerService.getCustomers()
      .then(response => {
        setData(response.data);
        console.log('Table:', response.data);
      })
      .catch(error => {
        console.error(error);
        showErrorToast('Error', 'Failed to fetch customers.');
      });
  }, []);

  const fetchCities = useCallback(() => {
    CustomerService.getCities()
      .then(response => {
        setCities(response.data);
        console.log('Cities:', response.data);
      })
      .catch(error => {
        console.error(error);
        showErrorToast('Error', 'Failed to fetch cities.');
      });
  }, []);

  const fetchDistricts = useCallback(cityId => {
    CustomerService.getDistricts(cityId)
      .then(response => {
        setDistricts(response.data);
        console.log('Districts:', response.data);
      })
      .catch(error => {
        console.error(error);
        showErrorToast('Error', 'Failed to fetch districts.');
      });
  }, []);

  useEffect(() => {
    fetchCustomers();
  }, [fetchCustomers]);

  useEffect(() => {
    fetchCities();
  }, [fetchCities]);

  useEffect(() => {
    if (selectedCity) {
      fetchDistricts(selectedCity.id);
    }
  }, [fetchDistricts, selectedCity]);

  const saveCustomer = () => {
    const customer = {
      firstName,
      lastName,
      email,
      districtDTO: {
        id: selectedDistrict?.id,
      },
      address,
    };

    CustomerService.saveCustomer(customer)
      .then(response => {
        console.log('New Customer Data:', response.data);
        setLogic(!logic);
        clearForm();
        showSuccessToast('Success', 'Customer saved successfully.');
        fetchCustomers(); // Yeni müşteri ekledikten sonra verileri yenile
      })
      .catch(error => {
        console.error(error);
        showErrorToast('Error', 'Failed to save customer.');
      });
  };

  const deleteCustomer = rowData => {
    CustomerService.deleteCustomer(rowData.id)
      .then(response => {
        console.log('Delete Customer:', response.data);
        setLogic(!logic);
        showSuccessToast('Success', 'Customer deleted successfully.');
        fetchCustomers(); // Müşteri silindikten sonra verileri yenile
      })
      .catch(error => {
        console.error(error);
        showErrorToast('Error', 'Failed to delete customer.');
      });
  };

  const showSuccessToast = (summary, detail) => {
    toast.current.show({ severity: 'success', summary, detail, life: 3000 });
  };

  const showErrorToast = (summary, detail) => {
    toast.current.show({ severity: 'error', summary, detail, life: 3000 });
  };

  const clearForm = () => {
    setFirstName('');
    setLastName('');
    setEmail('');
    setAddress('');
    setSelectedCity(null);
    setSelectedDistrict(null);
  };

  const onGlobalFilterChange = e => {
    const value = e.target.value;
    let _filters = { ...filters };

    _filters['global'].value = value;

    setFilters(_filters);
    setGlobalFilterValue(value);
  };

  const renderHeader = () => {
    return (
      <div className="flex flex-wrap align-items-center justify-content-between gap-2">
        <InputText value={globalFilterValue} onChange={onGlobalFilterChange} placeholder="Keyword Search" style={{ fontSize: '12px' }} />
      </div>
    );
  };

  const paginatorLeft = <Button style={{ fontSize: '10px' }} type="button" icon="pi pi-refresh" text />;
  const paginatorRight = <Button type="button" icon="pi pi-download" text />;

  const header = renderHeader();

  const actionBodyTemplate = rowData => {
    return (
      <Button
        type="button"
        icon="pi pi-trash"
        size="small"
        severity="danger"
        outlined
        aria-label="Cancel"
        rounded
        onClick={() => deleteCustomer(rowData)}
      />
    );
  };

  return (
    <div className="formgrid grid">
      <Toast ref={toast} />

      <br />
      <FloatLabel>
        <InputText id="firstname" value={firstName} onChange={e => setFirstName(e.target.value)} />
        <label htmlFor="firstname">First Name</label>
      </FloatLabel>
      <br />
      <FloatLabel>
        <InputText id="lastname" value={lastName} onChange={e => setLastName(e.target.value)} />
        <label htmlFor="lastname">Last Name</label>
      </FloatLabel>
      <br />
      <FloatLabel>
        <InputText id="email" value={email} onChange={e => setEmail(e.target.value)} />
        <label htmlFor="email">E-mail</label>
      </FloatLabel>
      <br />
      <FloatLabel>
        <Dropdown
          inputId="dd-city"
          value={selectedCity}
          onChange={e => setSelectedCity(e.value)}
          options={cities}
          optionLabel="name"
          className="w-full"
          style={{ width: 270 }}
        />
        <label htmlFor="dd-city">Select a City</label>
      </FloatLabel>
      <br />
      <FloatLabel>
        <Dropdown
          inputId="dd-district"
          value={selectedDistrict}
          onChange={e => setSelectedDistrict(e.value)}
          options={districts}
          optionLabel="name"
          className="w-full"
          style={{ width: 270 }}
        />
        <label htmlFor="dd-district">Select a District</label>
      </FloatLabel>
      <br />
      <FloatLabel>
        <InputTextarea id="address" value={address} onChange={e => setAddress(e.target.value)} rows={3} />
        <label htmlFor="address">Address</label>
      </FloatLabel>
      <br />
      <Button label="Save Customer" icon="pi pi-save" style={{ width: 200 }} onClick={saveCustomer} />
      <br />
      <br />
      <DataTable
        value={data}
        paginator
        showGridlines
        rows={25}
        rowsPerPageOptions={[5, 10, 25, 50]}
        tableStyle={{ minWidth: '50rem' }}
        paginatorTemplate="FirstPageLink PrevPageLink CurrentPageReport NextPageLink LastPageLink RowsPerPageDropdown"
        currentPageReportTemplate="{first} to {last} of {totalRecords}"
        paginatorClassName=""
        paginatorLeft={paginatorLeft}
        paginatorRight={paginatorRight}
        dataKey="id"
        filters={filters || {}} // filters değeri null ise boş bir nesne ataması yapılıyor
        globalFilterFields={['firstName', 'lastName', 'email', 'address', 'districtDTO.name']}
        header={header}
        style={{ fontSize: '12px' }}
        emptyMessage="No customers found."
      >
        <Column header="Sıra" body={(data, options) => options.rowIndex + 1} style={{ fontSize: '12px', textAlign: 'center' }} />
        <Column field="firstName" header="First Name" style={{ minWidth: '12rem' }} />
        <Column field="lastName" header="Last Name" style={{ minWidth: '12rem' }} />
        <Column field="email" header="Email" style={{ minWidth: '12rem' }} />
        <Column field="address" header="Address" style={{ minWidth: '12rem' }} />
        <Column field="districtDTO.name" header="District" style={{ minWidth: '12rem' }} />
        <Column header="Process" body={actionBodyTemplate} />
      </DataTable>
    </div>
  );
};
