import React, { useState } from 'react';
import 'primereact/resources/themes/lara-light-cyan/theme.css';
import 'primeicons/primeicons.css';
import { FloatLabel } from 'primereact/floatlabel';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { InputTextarea } from 'primereact/inputtextarea';
import { Dropdown } from 'primereact/dropdown';

const Customer = () => {
    const [value, setValue] = useState();

    const [selectedCity, setSelectedCity] = useState(null);
    const cities = [
        { name: 'New York', code: 'NY' },
        { name: 'Rome', code: 'RM' },
        { name: 'London', code: 'LDN' },
        { name: 'Istanbul', code: 'IST' },
        { name: 'Paris', code: 'PRS' },
      ];

    return (
        <div className="formgrid grid">
            <br />
            <FloatLabel>
              <InputText id="firstname" />
              <label htmlFor="firstname">First Name</label>
            </FloatLabel>
            <br />
            <FloatLabel>
              <InputText id="lastname" />
              <label htmlFor="lastname">Last Name</label>
            </FloatLabel>
            <br />
            <FloatLabel>
              <InputText id="email" />
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
                value={selectedCity}
                onChange={e => setSelectedCity(e.value)}
                options={cities}
                optionLabel="name"
                className="w-full"
                style={{ width: 270 }}
              />
              <label htmlFor="dd-city">Select a District</label>
            </FloatLabel>
            <br />
            <FloatLabel>
              <InputTextarea id="address" value={value} onChange={e => setValue(e.target.value)} rows={5} cols={30} />
              <label htmlFor="address">Address</label>
            </FloatLabel>
            <br />
            <Button label="Save Customer" icon="pi pi-save" style={{ width: 200 }} />
          </div>
    );
};

export default Customer;