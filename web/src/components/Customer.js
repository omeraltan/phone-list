import React, { useState, useEffect } from 'react';
import axios from 'axios';
import 'primereact/resources/themes/lara-light-cyan/theme.css';
import 'primeicons/primeicons.css';
import { FloatLabel } from 'primereact/floatlabel';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { InputTextarea } from 'primereact/inputtextarea';
import { Dropdown } from 'primereact/dropdown';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';

const Customer = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [address, setAddress] = useState('');
  const [selectedCity, setSelectedCity] = useState(null);
  const [selectedDistrict, setSelectedDistrict] = useState(null);
  const [cities, setCities] = useState([]);
  const [district, setDistrict] = useState([]);
  const [filters, setFilters] = useState(null);
  const [data, setData] = useState(null);
  const [globalFilterValue, setGlobalFilterValue] = useState('');
  const [logic, setLogic] = useState(false);

  useEffect(() => {
    axios
    .get('http://localhost:8080/api/customer')
    .then(response => {
      setData(response.data);
      console.log("Table:", response.data );
    })
    .catch(error => {
      console.log(error);
    });
  }, [logic])

  useEffect(() => {
    axios
      .get('http://localhost:8080/api/customer/cities/-1')
      .then(response => {
        setCities(response.data);
        console.log('Citites : ', response?.data);
      })
      .catch(error => {
        console.log(error);
      });

    if (selectedCity !== null && selectedCity !== undefined) {
      axios
        .get(`http://localhost:8080/api/customer/districts/${selectedCity?.id}`)
        .then(response => {
          setDistrict(response.data);
          console.log('Districts : ', response?.data);
        })
        .catch(error => {
          console.log(error);
        });
    }
  }, [selectedCity]);

  const saveCustomer = () => {
    const values = {
      firstName: firstName,
      lastName: lastName,
      email: email,
      districtDTO: {
        id: selectedDistrict?.id,
      },

      address: address,
    };
    axios
      .post('http://localhost:8080/api/customer', values)
      .then(response => {
        console.log('Yeni Customer Veri: ', response.data);
      })
      .catch(error => {
        console.log(error);
      });
    setFirstName('');
    setLastName('');
    setEmail('');
    setSelectedCity(null);
    setSelectedDistrict(null);

    //window.location.reload();
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

  return (
    <div className="formgrid grid">
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
          inputId="dd-city"
          value={selectedDistrict}
          onChange={e => setSelectedDistrict(e.value)}
          options={district}
          optionLabel="name"
          className="w-full"
          style={{ width: 270 }}
        />
        <label htmlFor="dd-city">Select a District</label>
      </FloatLabel>
      <br />
      <FloatLabel>
        <InputTextarea id="address" value={address} onChange={e => setAddress(e.target.value)} rows={5} cols={30} />
        <label htmlFor="address">Address</label>
      </FloatLabel>
      <br />
      <Button label="Save Customer" icon="pi pi-save" style={{ width: 200 }} onClick={saveCustomer} />
      <br />
      <br />
      <DataTable value={data}
        paginator showGridlines rows={25}
        rowsPerPageOptions={[5, 10, 25, 50]}
        tableStyle={{ minWidth: '50rem' }}
        paginatorTemplate="FirstPageLink PrevPageLink CurrentPageReport NextPageLink LastPageLink RowsPerPageDropdown"
        currentPageReportTemplate="{first} to {last} of {totalRecords}" paginatorClassName="" paginatorLeft={paginatorLeft}
        paginatorRight={paginatorRight}
        //loading={loading}
        dataKey="id" filters={filters}
        globalFilterFields={['name', 'country.name', 'representative.name', 'balance', 'status']}
        header={header}
        style={{ fontSize: '12px' }}
        emptyMessage="No customers found." >
        <Column header="Sıra" body={(data, options) => options.rowIndex + 1} style={{ fontSize: '12px', textAlign: 'center' }} />
        <Column field="firstName" header="First Name" style={{ minWidth: '12rem' }} />
        <Column field="lastName" header="Last Name" style={{ minWidth: '12rem' }} />
        <Column field="email" header="Email" style={{ minWidth: '12rem' }} />
        <Column field="address" header="Address" style={{ minWidth: '12rem' }} />
        <Column field="districtDTO.name" header="District" style={{ minWidth: '12rem' }} />
      </DataTable>
    </div>
  );
};

export default Customer;
