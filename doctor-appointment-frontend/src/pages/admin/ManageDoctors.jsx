import { useEffect, useState } from "react";
import { getAllDoctors, createDoctor } from "../../services/doctorService";
import { getAllSpecialties } from "../../services/specialtyService";

const s = {
  page: { padding: "28px", maxWidth: "960px", margin: "0 auto" },
  heading: { fontSize: "20px", fontWeight: "700", color: "#1e293b", marginBottom: "20px" },
  card: { background: "#fff", padding: "20px", borderRadius: "8px", border: "1px solid #e2e8f0", marginBottom: "24px" },
  cardTitle: { fontSize: "15px", fontWeight: "600", color: "#334155", marginBottom: "14px" },
  row: { display: "flex", gap: "12px", flexWrap: "wrap" },
  group: { display: "flex", flexDirection: "column" },
  label: { fontSize: "12px", color: "#64748b", marginBottom: "4px", fontWeight: "500" },
  input: { padding: "8px 12px", border: "1px solid #cbd5e1", borderRadius: "6px", fontSize: "14px", width: "140px" },
  select: { padding: "8px 12px", border: "1px solid #cbd5e1", borderRadius: "6px", fontSize: "14px" },
  btn: { padding: "8px 18px", background: "#2563eb", color: "#fff", border: "none", borderRadius: "6px", fontSize: "14px", cursor: "pointer", alignSelf: "flex-end" },
  table: { width: "100%", borderCollapse: "collapse", background: "#fff", borderRadius: "8px", border: "1px solid #e2e8f0" },
  th: { background: "#f8fafc", padding: "11px 16px", textAlign: "left", fontSize: "12px", fontWeight: "600", color: "#64748b", textTransform: "uppercase", borderBottom: "1px solid #e2e8f0" },
  td: { padding: "11px 16px", fontSize: "14px", color: "#334155", borderBottom: "1px solid #f1f5f9" },
  msg: (ok) => ({ fontSize: "13px", marginTop: "10px", color: ok ? "#16a34a" : "#dc2626" }),
};

export default function ManageDoctors() {
  const [doctors, setDoctors] = useState([]);
  const [specialties, setSpecialties] = useState([]);
  const [form, setForm] = useState({ userId: "", specialtyId: "", mode: "ONLINE", experienceYears: "", consultationFee: "" });
  const [msg, setMsg] = useState({ text: "", ok: true });

  const fetchAll = () => {
    getAllDoctors().then((r) => setDoctors(r.data));
    getAllSpecialties().then((r) => setSpecialties(r.data));
  };
  useEffect(() => { fetchAll(); }, []);

  const handleCreate = async () => {
    try {
      const res = await createDoctor({
        userId: Number(form.userId),
        specialtyId: Number(form.specialtyId),
        mode: form.mode,
        experienceYears: Number(form.experienceYears),
        consultationFee: Number(form.consultationFee),
      });
      setMsg({ text: `Doctor profile created! Doctor ID: ${res.data.id}`, ok: true });
      fetchAll();
    } catch (err) {
      setMsg({ text: err.response?.data?.message || "Failed to create.", ok: false });
    }
  };

  return (
    <div style={s.page}>
      <div style={s.heading}>Manage Doctors</div>
      <div style={s.card}>
        <div style={s.cardTitle}>Create Doctor Profile</div>
        <div style={s.row}>
          {[["User ID", "userId"], ["Experience (yrs)", "experienceYears"], ["Fee (₹)", "consultationFee"]].map(([label, key]) => (
            <div style={s.group} key={key}>
              <label style={s.label}>{label}</label>
              <input style={s.input} type="number" value={form[key]}
                onChange={(e) => setForm({ ...form, [key]: e.target.value })} />
            </div>
          ))}
          <div style={s.group}>
            <label style={s.label}>Specialty</label>
            <select style={s.select} value={form.specialtyId}
              onChange={(e) => setForm({ ...form, specialtyId: e.target.value })}>
              <option value="">Select</option>
              {specialties.map((sp) => <option key={sp.id} value={sp.id}>{sp.name}</option>)}
            </select>
          </div>
          <div style={s.group}>
            <label style={s.label}>Mode</label>
            <select style={s.select} value={form.mode} onChange={(e) => setForm({ ...form, mode: e.target.value })}>
              <option value="ONLINE">Online</option>
              <option value="OFFLINE">Offline</option>
            </select>
          </div>
          <button style={s.btn} onClick={handleCreate}>Create</button>
        </div>
        {msg.text && <div style={s.msg(msg.ok)}>{msg.text}</div>}
      </div>
      <table style={s.table}>
        <thead>
          <tr>
            <th style={s.th}>Doctor ID</th>
            <th style={s.th}>User ID</th>
            <th style={s.th}>Specialty ID</th>
            <th style={s.th}>Mode</th>
            <th style={s.th}>Experience</th>
            <th style={s.th}>Fee</th>
          </tr>
        </thead>
        <tbody>
          {doctors.map((d) => (
            <tr key={d.id}>
              <td style={s.td}>{d.id}</td>
              <td style={s.td}>{d.userId}</td>
              <td style={s.td}>{d.specialtyId}</td>
              <td style={s.td}>{d.mode}</td>
              <td style={s.td}>{d.experienceYears} yrs</td>
              <td style={s.td}>₹{d.consultationFee}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}