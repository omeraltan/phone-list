import React, { useState, useEffect, useRef, useCallback } from 'react';
import { Toast } from 'primereact/toast';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { Dropdown } from 'primereact/dropdown';
import { FloatLabel } from 'primereact/floatlabel';
import { InputMask } from 'primereact/inputmask';
import PhoneService from '../services/PhoneService';

const CustomerPhoneList = () => {
  const [customers, setCustomers] = useState([]);
  const [selectedCustomer, setSelectedCustomer] = useState(null);
  const [number, setNumber] = useState('');
  const [data, setData] = useState([]);
  const [filters, setFilters] = useState({ global: { value: null, matchMode: 'contains' } });
  const [globalFilterValue, setGlobalFilterValue] = useState('');
  const toast = useRef(null);

  const fetchPhoneData = useCallback(() => {
    PhoneService.getPhones()
      .then(response => {
        setData(response.data);
        console.log('PhoneCustomerTable:', response.data);
      })
      .catch(error => {
        console.error(error);
        showErrorToast('Error', 'Failed to fetch phone data.');
      });
  }, []);

  const fetchCustomerData = useCallback(() => {
    PhoneService.getCustomers()
      .then(response => {
        setCustomers(response.data);
        console.log('Customers:', response.data);
      })
      .catch(error => {
        console.error(error);
        showErrorToast('Error', 'Failed to fetch customers.');
      });
  }, []);

  useEffect(() => {
    fetchPhoneData();
    fetchCustomerData();
  }, [fetchPhoneData, fetchCustomerData]);

  const handleSavePhone = () => {
    console.log('Phone Number:', number);
    const values = {
      phoneNumber: number,
      customerDTO: {
        id: selectedCustomer?.id,
      },
    };
    PhoneService.savePhone(values)
      .then(response => {
        console.log('New Phone Data:', response.data);
        clearForm();
        fetchPhoneData(); // Save işleminden sonra verileri yenile
        showSuccessToast('Success', 'Phone saved successfully.');
      })
      .catch(error => {
        console.error(error);
        showErrorToast('Error', 'Failed to save phone.');
      });
    setSelectedCustomer(null);
    setNumber('');
  };

  const deletePhone = rowData => {
    PhoneService.deletePhone(rowData.id)
      .then(response => {
        console.log('Delete Phone:', response.data);
        showSuccessToast('Success', 'Phone deleted successfully.');
        fetchPhoneData(); // Telefon silindikten sonra verileri yenile
      })
      .catch(error => {
        console.error(error);
        showErrorToast('Error', 'Failed to delete phone.');
      });
  };

  const showSuccessToast = (summary, detail) => {
    toast.current.show({ severity: 'success', summary, detail, life: 3000 });
  };

  const showErrorToast = (summary, detail) => {
    toast.current.show({ severity: 'error', summary, detail, life: 3000 });
  };

  const clearForm = () => {
    setSelectedCustomer(null);
    setNumber('');
  };

  const onGlobalFilterChange = e => {
    const { value } = e.target;
    setGlobalFilterValue(value);

    let _filters = { ...filters };
    if (!_filters['global']) {
      _filters['global'] = { value: null, matchMode: 'contains' };
    }
    _filters['global'].value = value;

    setFilters(_filters);
  };

  const resetFilter = () => {
    setFilters({ global: { value: null, matchMode: 'contains' } });
    setGlobalFilterValue('');
  };

  const renderHeader = () => {
    return (
      <div className="header-container">
        <InputText value={globalFilterValue} onChange={onGlobalFilterChange} placeholder="Keyword Search" style={{ fontSize: '12px' }} />
        <Button label="Reset" icon="pi pi-refresh" className="reset-button" iconPos="right" onClick={resetFilter} />
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
        onClick={() => deletePhone(rowData)}
      />
    );
  };

  return (
    <div className="formgrid grid">
      <Toast ref={toast} />

      <div>
        <FloatLabel>
          <Dropdown
            inputId="dd-customer"
            value={selectedCustomer}
            onChange={e => setSelectedCustomer(e.value)}
            options={customers}
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
        <Button label="Save Phone" icon="pi pi-save" style={{ width: 200 }} onClick={handleSavePhone} />
      </div>
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
        filters={filters}
        globalFilterFields={['customerDTO.firstName', 'customerDTO.lastName', 'phoneNumber', 'customerDTO.email', 'customerDTO.address', 'customerDTO.districtDTO.name']}
        header={header}
        style={{ fontSize: '12px' }}
        emptyMessage="No customers found."
      >
        <Column header="Index" body={(data, options) => options.rowIndex + 1} style={{ fontSize: '12px', textAlign: 'center' }} />
        <Column field="customerDTO.firstName" header="First Name" style={{ minWidth: '12rem' }} />
        <Column field="customerDTO.lastName" header="Last Name" style={{ minWidth: '12rem' }} />
        <Column field="phoneNumber" header="Phone" style={{ minWidth: '12rem' }} />
        <Column field="customerDTO.email" header="E-mail" style={{ minWidth: '14rem' }} />
        <Column field="customerDTO.address" header="Address" style={{ minWidth: '14rem' }} />
        <Column field="customerDTO.districtDTO.name" header="City" style={{ minWidth: '10rem' }} />
        <Column header="Process" body={actionBodyTemplate} />
      </DataTable>
    </div>
  );
};
export default CustomerPhoneList;