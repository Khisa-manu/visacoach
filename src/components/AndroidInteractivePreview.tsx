import React, { useState, useEffect } from 'react';
import { 
  Smartphone, CheckCircle2, AlertCircle, Play, Mic, MicOff, Phone, 
  RotateCcw, Shield, Award, ChevronRight, ArrowLeft, Send, Sparkles, 
  CreditCard, User, History, Settings, ExternalLink, Check, Lock, Info,
  Compass, FileText, HelpCircle, ArrowRight
} from 'lucide-react';

type ScreenId = 'WELCOME' | 'REGISTER' | 'LOGIN' | 'OTP' | 'DASHBOARD' | 'INTERVIEW' | 'RESULTS' | 'PROFILE' | 'MPESA';

export const AndroidInteractivePreview: React.FC = () => {
  const [currentScreen, setCurrentScreen] = useState<ScreenId>('WELCOME');
  
  // Registration / Login Form State
  const [fullName, setFullName] = useState('Brian Kiprono Mwangi');
  const [phoneNumber, setPhoneNumber] = useState('0712345678');
  const [otpCode, setOtpCode] = useState('849201');
  const [authLoading, setAuthLoading] = useState(false);

  // M-Pesa Simulation State
  const [mpesaState, setMpesaState] = useState<'IDLE' | 'SENDING' | 'PIN_PROMPT' | 'SUCCESS'>('IDLE');
  const [mpesaPin, setMpesaPin] = useState('');

  // Voice Interview Simulation State
  const [interviewState, setInterviewState] = useState<'SPEAKING' | 'LISTENING' | 'THINKING'>('SPEAKING');
  const [speechTimer, setSpeechTimer] = useState(14);
  const [isMicActive, setIsMicActive] = useState(false);

  // Profile State
  const [profilePurpose, setProfilePurpose] = useState('Attending Annual Tech Summit & 5-day holiday in New York');
  const [profileEmployer, setProfileEmployer] = useState('Safaricom PLC (Senior Systems Engineer, 4 years)');
  const [profileIncome, setProfileIncome] = useState('KES 280,000 / month');
  const [profileSaved, setProfileSaved] = useState(false);

  useEffect(() => {
    let interval: any;
    if (currentScreen === 'INTERVIEW') {
      interval = setInterval(() => {
        setSpeechTimer(t => (t > 0 ? t - 1 : 15));
      }, 1000);
    }
    return () => clearInterval(interval);
  }, [currentScreen]);

  const handleStartInterview = () => {
    setCurrentScreen('INTERVIEW');
    setInterviewState('SPEAKING');
    setSpeechTimer(14);
  };

  const handleMpesaStk = () => {
    setMpesaState('SENDING');
    setTimeout(() => {
      setMpesaState('PIN_PROMPT');
    }, 1200);
  };

  const submitMpesaPin = () => {
    setMpesaState('SUCCESS');
    setTimeout(() => {
      setMpesaState('IDLE');
      setCurrentScreen('DASHBOARD');
    }, 2000);
  };

  const screensList: { id: ScreenId; label: string; icon: any; category: string }[] = [
    { id: 'WELCOME', label: 'Welcome / Onboarding', icon: Sparkles, category: 'Onboarding' },
    { id: 'REGISTER', label: 'Register (Kenyan Phone)', icon: User, category: 'Onboarding' },
    { id: 'LOGIN', label: 'Login & OTP', icon: Lock, category: 'Onboarding' },
    { id: 'DASHBOARD', label: 'Dashboard & Readiness', icon: Smartphone, category: 'Core App' },
    { id: 'INTERVIEW', label: 'Voice Mock Interview', icon: Mic, category: 'Core App' },
    { id: 'RESULTS', label: '214(b) Evaluation', icon: Award, category: 'Core App' },
    { id: 'PROFILE', label: 'DS-160 Profile', icon: FileText, category: 'Settings' },
    { id: 'MPESA', label: 'Safaricom M-Pesa STK', icon: CreditCard, category: 'Billing' },
  ];

  return (
    <div className="space-y-6">
      {/* Top Banner Notice */}
      <div className="bg-gradient-to-r from-[#0A192F] via-slate-900 to-[#0A2540] rounded-2xl p-5 text-white shadow-md flex flex-col md:flex-row items-start md:items-center justify-between gap-4">
        <div>
          <div className="flex items-center gap-2 mb-1">
            <span className="px-2.5 py-0.5 rounded-full text-xs font-bold bg-teal-400/20 text-teal-300 border border-teal-400/30">
              Interactive Android App Preview
            </span>
            <span className="text-xs text-slate-300 font-mono">com.visacoach.ui</span>
          </div>
          <h2 className="text-xl font-bold text-white">USA VisaCoach Mobile Experience</h2>
          <p className="text-xs text-slate-300 max-w-2xl">
            Test the full user journey: from the <strong>Welcome / Onboarding screen</strong> and <strong>OTP Authentication</strong> to the <strong>Voice Mock Interview</strong> and <strong>Safaricom M-Pesa STK Push</strong>.
          </p>
        </div>

        {/* Reset / Jump to Welcome */}
        <button
          onClick={() => setCurrentScreen('WELCOME')}
          className="flex items-center gap-2 px-3.5 py-2 rounded-xl bg-teal-500 hover:bg-teal-400 text-slate-950 text-xs font-bold shadow-sm transition-all shrink-0"
        >
          <RotateCcw className="w-3.5 h-3.5" />
          Reset to Welcome Screen
        </button>
      </div>

      {/* Screen Navigation Bar */}
      <div className="bg-white border border-slate-200 rounded-xl p-2.5 shadow-xs flex items-center gap-2 overflow-x-auto">
        <span className="text-xs font-bold text-slate-500 uppercase tracking-wider px-2 shrink-0">
          Screens:
        </span>
        <div className="flex items-center gap-1.5 flex-nowrap">
          {screensList.map((s) => {
            const IconComponent = s.icon;
            const isActive = currentScreen === s.id;
            return (
              <button
                key={s.id}
                onClick={() => setCurrentScreen(s.id)}
                className={`flex items-center gap-1.5 px-3 py-1.5 rounded-lg text-xs font-semibold whitespace-nowrap transition-all ${
                  isActive 
                    ? 'bg-[#0A192F] text-white shadow-xs' 
                    : 'text-slate-600 hover:text-slate-900 hover:bg-slate-100'
                }`}
              >
                <IconComponent className={`w-3.5 h-3.5 ${isActive ? 'text-teal-400' : 'text-slate-400'}`} />
                {s.label}
              </button>
            );
          })}
        </div>
      </div>

      {/* Main Two-Column Layout: Mobile Device Simulator + Screen Context Guide */}
      <div className="grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">
        
        {/* Left Column: Authentic Android Phone Frame */}
        <div className="lg:col-span-5 flex justify-center">
          <div className="w-full max-w-[390px] bg-slate-950 rounded-[46px] p-3.5 shadow-2xl border-[5px] border-slate-800 relative">
            
            {/* Camera Hole Punch */}
            <div className="absolute top-6 left-1/2 -translate-x-1/2 w-4 h-4 bg-black rounded-full border border-slate-700 z-50 flex items-center justify-center">
              <div className="w-1.5 h-1.5 bg-blue-950 rounded-full" />
            </div>

            {/* Android Screen Bezel */}
            <div className="w-full bg-[#F8FAFC] rounded-[36px] overflow-hidden flex flex-col min-h-[660px] max-h-[720px] shadow-inner relative text-slate-900 font-sans select-none">
              
              {/* Android Status Bar */}
              <div className="w-full bg-[#0A192F] text-white px-6 pt-2 pb-1.5 flex items-center justify-between text-[11px] font-semibold tracking-tight z-40">
                <span>09:41</span>
                <div className="flex items-center gap-1.5 opacity-90">
                  <span className="text-[10px]">5G</span>
                  <div className="w-4 h-2.5 border border-white/80 rounded-sm p-0.5 flex items-center">
                    <div className="h-full w-3/4 bg-white rounded-2xs" />
                  </div>
                </div>
              </div>

              {/* ════════════════════════════════════════════════════════════
                  SCREEN 1: WELCOME / ONBOARDING SCREEN
                  (Direct replica of AuthScreens.kt: WelcomeScreen)
              ════════════════════════════════════════════════════════════ */}
              {currentScreen === 'WELCOME' && (
                <div className="flex-1 overflow-y-auto px-6 py-6 flex flex-col justify-between text-center bg-[#F8FAFC]">
                  {/* Top Brand Header */}
                  <div className="flex flex-col items-center">
                    <div className="flex items-center justify-center gap-2 pt-2">
                      <Shield className="w-5 h-5 text-[#0D9488]" />
                      <span className="text-base font-black text-[#0A192F] tracking-tight">USA VisaCoach</span>
                    </div>

                    {/* Central Hero Icon Card */}
                    <div className="mt-8 mb-6 w-28 h-28 rounded-3xl bg-white shadow-md border border-slate-100 flex items-center justify-center">
                      <div className="w-20 h-20 rounded-2xl bg-slate-50 flex items-center justify-center">
                        <Mic className="w-10 h-10 text-[#0A192F]" />
                      </div>
                    </div>

                    {/* Display Title */}
                    <h2 className="text-2xl font-black text-[#0A192F] leading-tight">
                      Master Your<br />U.S. Visa Interview
                    </h2>

                    {/* Subtitle */}
                    <p className="text-xs text-slate-600 mt-3 px-2 leading-relaxed">
                      Realistic AI voice mock interviews for B1/B2 applicants in Kenya. Build confidence before your appointment.
                    </p>

                    {/* Feature Highlights Pills */}
                    <div className="mt-6 space-y-2 w-full text-left">
                      <div className="flex items-center gap-2.5 p-2.5 bg-white rounded-xl border border-slate-200/80 shadow-2xs">
                        <div className="w-6 h-6 rounded-lg bg-teal-50 flex items-center justify-center shrink-0">
                          <Check className="w-3.5 h-3.5 text-teal-600" />
                        </div>
                        <span className="text-[11px] font-medium text-slate-700">Real-time voice simulation with Consular AI</span>
                      </div>
                      <div className="flex items-center gap-2.5 p-2.5 bg-white rounded-xl border border-slate-200/80 shadow-2xs">
                        <div className="w-6 h-6 rounded-lg bg-blue-50 flex items-center justify-center shrink-0">
                          <Check className="w-3.5 h-3.5 text-blue-600" />
                        </div>
                        <span className="text-[11px] font-medium text-slate-700">INA Section 214(b) non-immigrant intent scoring</span>
                      </div>
                      <div className="flex items-center gap-2.5 p-2.5 bg-white rounded-xl border border-slate-200/80 shadow-2xs">
                        <div className="w-6 h-6 rounded-lg bg-emerald-50 flex items-center justify-center shrink-0">
                          <Check className="w-3.5 h-3.5 text-emerald-600" />
                        </div>
                        <span className="text-[11px] font-medium text-slate-700">Instant Safaricom M-Pesa Daraja payment</span>
                      </div>
                    </div>
                  </div>

                  {/* Action Buttons */}
                  <div className="space-y-2.5 pt-6 pb-2">
                    <button
                      onClick={() => setCurrentScreen('REGISTER')}
                      className="w-full h-12 rounded-[14px] bg-[#0A192F] text-white font-bold text-xs flex items-center justify-center shadow-md hover:bg-slate-900 active:scale-[0.99] transition-all"
                    >
                      Get Started
                    </button>
                    <button
                      onClick={() => setCurrentScreen('LOGIN')}
                      className="w-full h-11 rounded-[14px] border-[1.5px] border-[#0A192F] text-[#0A192F] font-bold text-xs flex items-center justify-center hover:bg-slate-100 transition-all"
                    >
                      I already have an account
                    </button>
                  </div>
                </div>
              )}

              {/* ════════════════════════════════════════════════════════════
                  SCREEN 2: REGISTER SCREEN
              ════════════════════════════════════════════════════════════ */}
              {currentScreen === 'REGISTER' && (
                <div className="flex-1 overflow-y-auto px-6 py-5 flex flex-col justify-between text-left bg-[#F8FAFC]">
                  <div>
                    {/* Brand header */}
                    <div className="flex items-center gap-2 mb-6">
                      <Shield className="w-4 h-4 text-[#0D9488]" />
                      <span className="text-sm font-black text-[#0A192F]">USA VisaCoach</span>
                    </div>

                    <h3 className="text-xl font-bold text-[#0A192F]">Create Account</h3>
                    <p className="text-xs text-slate-500 mt-1">
                      Sign up with your Kenyan mobile number to start realistic visa mock interviews.
                    </p>

                    <div className="space-y-4 mt-6">
                      {/* Name field */}
                      <div>
                        <label className="block text-[11px] font-semibold text-slate-700 mb-1.5">
                          Full Legal Name
                        </label>
                        <input
                          type="text"
                          value={fullName}
                          onChange={(e) => setFullName(e.target.value)}
                          placeholder="As shown on your passport / DS-160"
                          className="w-full px-3 py-2.5 rounded-xl border border-slate-300 text-xs text-slate-900 focus:outline-hidden focus:border-[#0A192F] bg-white shadow-2xs"
                        />
                      </div>

                      {/* Phone field */}
                      <div>
                        <label className="block text-[11px] font-semibold text-slate-700 mb-1.5">
                          Mobile Phone Number
                        </label>
                        <div className="flex items-center rounded-xl border border-slate-300 bg-white px-3 py-2 text-xs shadow-2xs">
                          <span className="font-bold text-[#00843D] pr-2 border-r border-slate-200">+254</span>
                          <input
                            type="text"
                            value={phoneNumber}
                            onChange={(e) => setPhoneNumber(e.target.value)}
                            className="pl-2 w-full outline-hidden text-slate-800 font-mono text-xs"
                            placeholder="0712 345 678"
                          />
                        </div>
                        <p className="text-[10px] text-slate-400 mt-1">
                          You will receive a 6-digit SMS verification code.
                        </p>
                      </div>
                    </div>
                  </div>

                  <div className="space-y-2.5 pt-6 pb-2">
                    <button
                      onClick={() => {
                        setAuthLoading(true);
                        setTimeout(() => {
                          setAuthLoading(false);
                          setCurrentScreen('OTP');
                        }, 800);
                      }}
                      className="w-full h-12 rounded-[14px] bg-[#0A192F] text-white font-bold text-xs flex items-center justify-center shadow-md hover:bg-slate-900 transition-all"
                    >
                      {authLoading ? (
                        <div className="w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin" />
                      ) : (
                        'Create Account & Request OTP'
                      )}
                    </button>
                    <button
                      onClick={() => setCurrentScreen('LOGIN')}
                      className="w-full py-2 text-xs text-slate-600 font-medium hover:text-slate-900 text-center"
                    >
                      Already have an account? <span className="font-bold text-[#0A192F]">Sign in</span>
                    </button>
                  </div>
                </div>
              )}

              {/* ════════════════════════════════════════════════════════════
                  SCREEN 3: LOGIN SCREEN
              ════════════════════════════════════════════════════════════ */}
              {currentScreen === 'LOGIN' && (
                <div className="flex-1 overflow-y-auto px-6 py-5 flex flex-col justify-between text-left bg-[#F8FAFC]">
                  <div>
                    <div className="flex items-center gap-2 mb-6">
                      <Shield className="w-4 h-4 text-[#0D9488]" />
                      <span className="text-sm font-black text-[#0A192F]">USA VisaCoach</span>
                    </div>

                    <h3 className="text-xl font-bold text-[#0A192F]">Welcome Back</h3>
                    <p className="text-xs text-slate-500 mt-1">
                      Sign in with your registered Kenyan phone number.
                    </p>

                    <div className="space-y-4 mt-6">
                      <div>
                        <label className="block text-[11px] font-semibold text-slate-700 mb-1.5">
                          Mobile Phone Number
                        </label>
                        <div className="flex items-center rounded-xl border border-slate-300 bg-white px-3 py-2 text-xs shadow-2xs">
                          <span className="font-bold text-[#00843D] pr-2 border-r border-slate-200">+254</span>
                          <input
                            type="text"
                            value={phoneNumber}
                            onChange={(e) => setPhoneNumber(e.target.value)}
                            className="pl-2 w-full outline-hidden text-slate-800 font-mono text-xs"
                            placeholder="0712 345 678"
                          />
                        </div>
                      </div>
                    </div>
                  </div>

                  <div className="space-y-2.5 pt-6 pb-2">
                    <button
                      onClick={() => {
                        setAuthLoading(true);
                        setTimeout(() => {
                          setAuthLoading(false);
                          setCurrentScreen('OTP');
                        }, 700);
                      }}
                      className="w-full h-12 rounded-[14px] bg-[#0A192F] text-white font-bold text-xs flex items-center justify-center shadow-md hover:bg-slate-900 transition-all"
                    >
                      {authLoading ? (
                        <div className="w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin" />
                      ) : (
                        'Request OTP Code'
                      )}
                    </button>
                    <button
                      onClick={() => setCurrentScreen('REGISTER')}
                      className="w-full py-2 text-xs text-slate-600 font-medium hover:text-slate-900 text-center"
                    >
                      Don't have an account? <span className="font-bold text-[#0A192F]">Register</span>
                    </button>
                  </div>
                </div>
              )}

              {/* ════════════════════════════════════════════════════════════
                  SCREEN 4: OTP SCREEN
              ════════════════════════════════════════════════════════════ */}
              {currentScreen === 'OTP' && (
                <div className="flex-1 overflow-y-auto px-6 py-5 flex flex-col justify-between text-left bg-[#F8FAFC]">
                  <div>
                    <button 
                      onClick={() => setCurrentScreen('LOGIN')}
                      className="flex items-center gap-1 text-xs text-slate-500 hover:text-slate-900 mb-4"
                    >
                      <ArrowLeft className="w-3.5 h-3.5" /> Back
                    </button>

                    <h3 className="text-xl font-bold text-[#0A192F]">Verify Code</h3>
                    <p className="text-xs text-slate-500 mt-1">
                      Enter the 6-digit verification code sent to <strong className="text-slate-800">+254 {phoneNumber}</strong>
                    </p>

                    <div className="mt-8">
                      <div className="flex justify-between gap-2">
                        {otpCode.split('').map((digit, i) => (
                          <div
                            key={i}
                            className="w-11 h-12 rounded-xl border-2 border-[#0A192F] bg-white flex items-center justify-center font-mono font-black text-base text-[#0A192F] shadow-2xs"
                          >
                            {digit}
                          </div>
                        ))}
                      </div>

                      <p className="text-[11px] text-slate-400 text-center mt-4">
                        Didn't receive code? <span className="text-teal-700 font-bold cursor-pointer">Resend in 42s</span>
                      </p>
                    </div>
                  </div>

                  <div className="space-y-2.5 pt-6 pb-2">
                    <button
                      onClick={() => setCurrentScreen('DASHBOARD')}
                      className="w-full h-12 rounded-[14px] bg-[#0A192F] text-white font-bold text-xs flex items-center justify-center shadow-md hover:bg-slate-900 transition-all"
                    >
                      Verify &amp; Continue to App
                    </button>
                  </div>
                </div>
              )}

              {/* ════════════════════════════════════════════════════════════
                  SCREEN 5: DASHBOARD SCREEN
              ════════════════════════════════════════════════════════════ */}
              {currentScreen === 'DASHBOARD' && (
                <div className="flex-1 overflow-y-auto p-4 space-y-4 text-left bg-[#F8FAFC]">
                  {/* Header Bar */}
                  <div className="flex items-center justify-between pb-1">
                    <div>
                      <span className="text-[10px] font-bold text-slate-500 uppercase tracking-wider">Welcome back</span>
                      <h3 className="text-base font-black text-[#0A192F]">{fullName}</h3>
                    </div>
                    <span className="px-2 py-0.5 bg-[#00843D]/10 text-[#00843D] font-bold text-[10px] rounded-full border border-[#00843D]/20">
                      PRO PLAN
                    </span>
                  </div>

                  {/* Visa Readiness Hero Card */}
                  <div className="p-4 bg-gradient-to-br from-[#0A192F] to-slate-900 rounded-2xl text-white shadow-md relative overflow-hidden">
                    <div className="flex items-center justify-between mb-2">
                      <span className="text-xs font-medium text-slate-300">INA 214(b) Visa Readiness</span>
                      <span className="text-xs font-bold text-teal-300">B1/B2 Visitor</span>
                    </div>
                    <div className="flex items-baseline gap-2 mb-2">
                      <span className="text-3xl font-black text-white">84%</span>
                      <span className="text-xs text-teal-300 font-semibold">Strong Candidate</span>
                    </div>
                    <div className="w-full bg-slate-700 h-2 rounded-full overflow-hidden">
                      <div className="bg-teal-400 h-full rounded-full w-[84%]" />
                    </div>
                    <p className="text-[10px] text-slate-400 mt-2">
                      3 mock interviews completed. Strong home ties confirmed.
                    </p>
                  </div>

                  {/* Start Interview CTA */}
                  <button
                    onClick={handleStartInterview}
                    className="w-full py-3.5 px-4 rounded-xl bg-[#0D9488] text-white font-bold text-xs flex items-center justify-between shadow-sm hover:opacity-95 active:scale-[0.99] transition-all"
                  >
                    <div className="flex items-center gap-2">
                      <div className="w-8 h-8 rounded-lg bg-white/20 flex items-center justify-center">
                        <Mic className="w-4 h-4 text-white" />
                      </div>
                      <div className="text-left">
                        <div className="leading-tight">Start Voice Mock Interview</div>
                        <div className="text-[10px] text-teal-100 font-normal">Real-time Consular Officer AI</div>
                      </div>
                    </div>
                    <ChevronRight className="w-4 h-4" />
                  </button>

                  {/* Quick Action Grid */}
                  <div className="grid grid-cols-2 gap-2 text-xs">
                    <div 
                      onClick={() => setCurrentScreen('RESULTS')}
                      className="p-3 bg-white border border-slate-200 rounded-xl cursor-pointer hover:border-teal-400 transition-colors shadow-2xs"
                    >
                      <Award className="w-4 h-4 text-teal-600 mb-1" />
                      <div className="font-bold text-slate-900 text-[11px]">Latest Score</div>
                      <div className="text-[10px] text-slate-500">8.4 / 10 Overall</div>
                    </div>
                    <div 
                      onClick={() => setCurrentScreen('MPESA')}
                      className="p-3 bg-white border border-slate-200 rounded-xl cursor-pointer hover:border-teal-400 transition-colors shadow-2xs"
                    >
                      <Shield className="w-4 h-4 text-[#00843D] mb-1" />
                      <div className="font-bold text-slate-900 text-[11px]">M-Pesa Access</div>
                      <div className="text-[10px] text-slate-500">Active 30 days</div>
                    </div>
                  </div>

                  {/* DS-160 Profile Card */}
                  <div 
                    onClick={() => setCurrentScreen('PROFILE')}
                    className="p-3.5 bg-white border border-slate-200 rounded-xl flex items-center justify-between cursor-pointer hover:border-slate-400 shadow-2xs"
                  >
                    <div className="flex items-center gap-3">
                      <div className="w-8 h-8 rounded-lg bg-blue-50 text-blue-700 flex items-center justify-center">
                        <FileText className="w-4 h-4" />
                      </div>
                      <div>
                        <div className="text-xs font-bold text-slate-900">DS-160 Profile Form</div>
                        <div className="text-[10px] text-slate-500">Employment, funds, and U.S. ties</div>
                      </div>
                    </div>
                    <ChevronRight className="w-4 h-4 text-slate-400" />
                  </div>
                </div>
              )}

              {/* ════════════════════════════════════════════════════════════
                  SCREEN 6: REAL-TIME VOICE MOCK INTERVIEW
              ════════════════════════════════════════════════════════════ */}
              {currentScreen === 'INTERVIEW' && (
                <div className="flex-1 overflow-y-auto p-4 flex flex-col justify-between bg-slate-900 text-white">
                  {/* Top Bar */}
                  <div className="flex items-center justify-between pb-2 border-b border-slate-800">
                    <button 
                      onClick={() => setCurrentScreen('DASHBOARD')}
                      className="p-1 rounded-lg bg-slate-800 text-slate-300 hover:text-white"
                    >
                      <ArrowLeft className="w-4 h-4" />
                    </button>
                    <div className="text-center">
                      <span className="text-[10px] font-bold text-slate-400 uppercase">Question 3 of 7</span>
                      <div className="text-xs font-bold text-teal-300">B1/B2 Purpose of Visit</div>
                    </div>
                    <div className="text-xs font-mono font-bold text-amber-400 bg-amber-950/60 px-2 py-0.5 rounded-md border border-amber-800/60">
                      00:{speechTimer < 10 ? `0${speechTimer}` : speechTimer}
                    </div>
                  </div>

                  {/* Consular Officer AI Visualizer */}
                  <div className="flex flex-col items-center justify-center py-6 text-center space-y-4">
                    <div className="relative">
                      <div className={`w-28 h-28 rounded-full border-4 flex items-center justify-center transition-all ${interviewState === 'SPEAKING' ? 'border-teal-400 animate-pulse bg-teal-950/40' : 'border-slate-700 bg-slate-800'}`}>
                        <Shield className="w-12 h-12 text-teal-400" />
                      </div>
                      <span className="absolute -bottom-2 left-1/2 -translate-x-1/2 px-2.5 py-0.5 rounded-full text-[10px] font-bold bg-teal-500 text-slate-950 shadow-md">
                        {interviewState === 'SPEAKING' ? 'OFFICER SPEAKING' : 'LISTENING TO YOU'}
                      </span>
                    </div>

                    {/* Consular Question Card */}
                    <div className="p-3.5 bg-slate-800/90 border border-slate-700 rounded-2xl text-left max-w-xs shadow-lg">
                      <p className="text-xs text-slate-200 leading-relaxed font-medium">
                        "Good morning. What is the specific purpose of your visit to the United States, and how long do you intend to stay?"
                      </p>
                    </div>

                    {/* Simulated Waveform */}
                    <div className="flex items-center gap-1 justify-center h-8">
                      {[18, 36, 12, 44, 28, 50, 22, 38, 14, 30].map((h, i) => (
                        <div
                          key={i}
                          style={{ height: `${h}px` }}
                          className={`w-1 rounded-full transition-all duration-200 ${interviewState === 'SPEAKING' ? 'bg-teal-400 animate-pulse' : 'bg-slate-700'}`}
                        />
                      ))}
                    </div>
                  </div>

                  {/* Mic Controls */}
                  <div className="space-y-3 pt-2">
                    <div className="flex items-center justify-center gap-4">
                      <button
                        onClick={() => setInterviewState(s => (s === 'SPEAKING' ? 'LISTENING' : 'SPEAKING'))}
                        className={`w-14 h-14 rounded-full flex items-center justify-center shadow-lg transition-all ${interviewState === 'LISTENING' ? 'bg-emerald-500 text-white animate-bounce' : 'bg-teal-500 text-slate-950'}`}
                      >
                        <Mic className="w-6 h-6" />
                      </button>
                    </div>

                    <button
                      onClick={() => setCurrentScreen('RESULTS')}
                      className="w-full py-2.5 rounded-xl bg-slate-800 hover:bg-slate-700 text-slate-300 font-bold text-xs border border-slate-700"
                    >
                      End Interview &amp; View 214(b) Evaluation
                    </button>
                  </div>
                </div>
              )}

              {/* ════════════════════════════════════════════════════════════
                  SCREEN 7: 214(b) EVALUATION RESULTS
              ════════════════════════════════════════════════════════════ */}
              {currentScreen === 'RESULTS' && (
                <div className="flex-1 overflow-y-auto p-4 space-y-4 text-left bg-[#F8FAFC]">
                  <div className="flex items-center justify-between pb-2 border-b border-slate-200">
                    <button 
                      onClick={() => setCurrentScreen('DASHBOARD')}
                      className="text-xs font-bold text-slate-600 flex items-center gap-1"
                    >
                      <ArrowLeft className="w-3.5 h-3.5" /> Dashboard
                    </button>
                    <span className="text-[10px] font-bold uppercase text-teal-700 bg-teal-50 px-2 py-0.5 rounded-full border border-teal-200">
                      Mock Complete
                    </span>
                  </div>

                  {/* Score Card */}
                  <div className="p-4 bg-gradient-to-br from-[#0A192F] to-slate-900 rounded-2xl text-white text-center">
                    <span className="text-[10px] font-bold text-slate-300 uppercase tracking-wider">Overall Evaluation Score</span>
                    <div className="text-4xl font-black text-teal-300 mt-1">8.4 / 10</div>
                    <p className="text-xs text-emerald-300 font-medium mt-1">
                      Low Risk of INA 214(b) Refusal
                    </p>
                  </div>

                  {/* Detailed Criteria Scores */}
                  <div className="space-y-2">
                    <span className="text-[11px] font-bold text-slate-700">6 Core Evaluation Pillars</span>
                    
                    <div className="p-2.5 bg-white border border-slate-200 rounded-xl space-y-1 text-xs shadow-2xs">
                      <div className="flex justify-between font-medium">
                        <span>Ties to Kenya (Family &amp; Property)</span>
                        <span className="font-bold text-emerald-600">88%</span>
                      </div>
                      <div className="w-full bg-slate-100 h-1.5 rounded-full overflow-hidden">
                        <div className="bg-emerald-500 h-full rounded-full w-[88%]" />
                      </div>
                    </div>

                    <div className="p-2.5 bg-white border border-slate-200 rounded-xl space-y-1 text-xs shadow-2xs">
                      <div className="flex justify-between font-medium">
                        <span>Financial Solvency &amp; Funds</span>
                        <span className="font-bold text-emerald-600">85%</span>
                      </div>
                      <div className="w-full bg-slate-100 h-1.5 rounded-full overflow-hidden">
                        <div className="bg-emerald-500 h-full rounded-full w-[85%]" />
                      </div>
                    </div>

                    <div className="p-2.5 bg-white border border-slate-200 rounded-xl space-y-1 text-xs shadow-2xs">
                      <div className="flex justify-between font-medium">
                        <span>Consistency with DS-160</span>
                        <span className="font-bold text-blue-600">92%</span>
                      </div>
                      <div className="w-full bg-slate-100 h-1.5 rounded-full overflow-hidden">
                        <div className="bg-blue-500 h-full rounded-full w-[92%]" />
                      </div>
                    </div>

                    <div className="p-2.5 bg-white border border-slate-200 rounded-xl space-y-1 text-xs shadow-2xs">
                      <div className="flex justify-between font-medium">
                        <span>Answer Conciseness &amp; Clarity</span>
                        <span className="font-bold text-teal-600">80%</span>
                      </div>
                      <div className="w-full bg-slate-100 h-1.5 rounded-full overflow-hidden">
                        <div className="bg-teal-500 h-full rounded-full w-[80%]" />
                      </div>
                    </div>
                  </div>

                  {/* Consular Officer Feedback */}
                  <div className="p-3 bg-amber-50 border border-amber-200 rounded-xl text-xs text-amber-900 space-y-1">
                    <span className="font-bold text-[11px] block text-amber-950">Consular Officer Observation:</span>
                    <p className="text-[11px] leading-relaxed">
                      "Good explanation of tenure at Safaricom PLC. Ensure you state your precise return date without hesitation when asked about travel duration."
                    </p>
                  </div>

                  <button
                    onClick={() => setCurrentScreen('DASHBOARD')}
                    className="w-full py-3 rounded-xl bg-[#0A192F] text-white font-bold text-xs shadow-md"
                  >
                    Return to Dashboard
                  </button>
                </div>
              )}

              {/* ════════════════════════════════════════════════════════════
                  SCREEN 8: PROFILE / DS-160 SCREEN
              ════════════════════════════════════════════════════════════ */}
              {currentScreen === 'PROFILE' && (
                <div className="flex-1 overflow-y-auto p-4 space-y-4 text-left bg-[#F8FAFC]">
                  <div className="flex items-center justify-between pb-2 border-b border-slate-200">
                    <button 
                      onClick={() => setCurrentScreen('DASHBOARD')}
                      className="text-xs font-bold text-slate-600 flex items-center gap-1"
                    >
                      <ArrowLeft className="w-3.5 h-3.5" /> Back
                    </button>
                    <span className="text-xs font-bold text-[#0A192F]">Applicant Profile</span>
                  </div>

                  <div className="p-3 bg-blue-50 border border-blue-200 rounded-xl text-[11px] text-blue-900 flex items-start gap-2">
                    <Info className="w-4 h-4 text-blue-600 shrink-0 mt-0.5" />
                    <span>Always align answers with your submitted DS-160 application form.</span>
                  </div>

                  <div className="space-y-3">
                    <div>
                      <label className="block text-[11px] font-semibold text-slate-700 mb-1">
                        Primary Purpose of Travel
                      </label>
                      <input
                        type="text"
                        value={profilePurpose}
                        onChange={(e) => setProfilePurpose(e.target.value)}
                        className="w-full px-3 py-2 rounded-xl border border-slate-300 text-xs bg-white text-slate-800 shadow-2xs"
                      />
                    </div>

                    <div>
                      <label className="block text-[11px] font-semibold text-slate-700 mb-1">
                        Employer / Company &amp; Tenure
                      </label>
                      <input
                        type="text"
                        value={profileEmployer}
                        onChange={(e) => setProfileEmployer(e.target.value)}
                        className="w-full px-3 py-2 rounded-xl border border-slate-300 text-xs bg-white text-slate-800 shadow-2xs"
                      />
                    </div>

                    <div>
                      <label className="block text-[11px] font-semibold text-slate-700 mb-1">
                        Monthly Income Range
                      </label>
                      <input
                        type="text"
                        value={profileIncome}
                        onChange={(e) => setProfileIncome(e.target.value)}
                        className="w-full px-3 py-2 rounded-xl border border-slate-300 text-xs bg-white text-slate-800 shadow-2xs"
                      />
                    </div>
                  </div>

                  {profileSaved && (
                    <div className="p-2 rounded-lg bg-emerald-50 text-emerald-800 text-xs font-bold border border-emerald-200 text-center">
                      Profile successfully synchronized with AI engine!
                    </div>
                  )}

                  <button
                    onClick={() => {
                      setProfileSaved(true);
                      setTimeout(() => setProfileSaved(false), 2000);
                    }}
                    className="w-full py-3 rounded-xl bg-[#0A192F] text-white font-bold text-xs shadow-md"
                  >
                    Save &amp; Update Profile
                  </button>
                </div>
              )}

              {/* ════════════════════════════════════════════════════════════
                  SCREEN 9: M-PESA STK PUSH CHECKOUT
              ════════════════════════════════════════════════════════════ */}
              {currentScreen === 'MPESA' && (
                <div className="flex-1 overflow-y-auto p-4 space-y-4 text-left relative bg-[#F8FAFC]">
                  {/* Top Bar */}
                  <div className="flex items-center justify-between pb-2 border-b border-slate-200">
                    <button 
                      onClick={() => setCurrentScreen('DASHBOARD')}
                      className="text-xs font-bold text-slate-600 flex items-center gap-1"
                    >
                      <ArrowLeft className="w-3.5 h-3.5" /> Back
                    </button>
                    <span className="text-xs font-bold text-[#00843D]">Lipa na M-Pesa</span>
                  </div>

                  {/* Pricing Plan Card */}
                  <div className="p-4 bg-gradient-to-br from-[#0A192F] to-slate-900 rounded-2xl text-white shadow-md">
                    <div className="flex justify-between items-start">
                      <div>
                        <span className="text-[10px] font-bold text-teal-300 uppercase tracking-wider">Upgrade Plan</span>
                        <h4 className="text-base font-black text-white">VisaCoach Pro Pass</h4>
                      </div>
                      <span className="px-2 py-0.5 rounded-full bg-[#00843D] text-white font-bold text-[10px]">
                        Lipa na M-Pesa
                      </span>
                    </div>

                    <div className="mt-3 flex items-baseline gap-1.5">
                      <span className="text-2xl font-black text-white">KES 1,499</span>
                      <span className="text-xs text-slate-300">/ 30 days unlimited</span>
                    </div>

                    <div className="mt-3 pt-3 border-t border-slate-700/80 space-y-1.5 text-[11px] text-slate-300">
                      <div className="flex items-center gap-2">
                        <Check className="w-3.5 h-3.5 text-teal-400" />
                        <span>Unlimited full voice mock sessions</span>
                      </div>
                      <div className="flex items-center gap-2">
                        <Check className="w-3.5 h-3.5 text-teal-400" />
                        <span>Instant 6-pillar 214(b) audit reports</span>
                      </div>
                    </div>
                  </div>

                  {/* Phone Input */}
                  <div className="space-y-2 pt-1">
                    <label className="text-[11px] font-bold text-slate-700 block">
                      Safaricom Mobile Number
                    </label>
                    <div className="flex items-center rounded-xl border border-slate-300 bg-white px-3 py-2 text-xs shadow-2xs">
                      <span className="font-bold text-[#00843D] pr-2 border-r border-slate-200">+254</span>
                      <input
                        type="text"
                        value={phoneNumber}
                        onChange={(e) => setPhoneNumber(e.target.value)}
                        className="pl-2 w-full outline-hidden text-slate-800 font-mono text-xs"
                        placeholder="0712 345 678"
                      />
                    </div>
                    <p className="text-[10px] text-slate-400">
                      You will receive an instant Lipa na M-Pesa PIN prompt on this phone.
                    </p>
                  </div>

                  {/* STK Push Trigger */}
                  <div className="pt-2">
                    <button
                      onClick={handleMpesaStk}
                      disabled={mpesaState !== 'IDLE'}
                      className="w-full h-12 rounded-[14px] bg-[#00843D] text-white font-bold text-xs flex items-center justify-center gap-2 shadow-md hover:bg-[#007033] active:scale-[0.99] transition-all disabled:opacity-50"
                    >
                      {mpesaState === 'SENDING' ? (
                        <div className="w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin" />
                      ) : (
                        <>
                          <Phone className="w-3.5 h-3.5" />
                          <span>Pay KES 1,499 via M-Pesa STK</span>
                        </>
                      )}
                    </button>
                  </div>

                  {/* Simulated USSD PIN Prompt Modal */}
                  {mpesaState === 'PIN_PROMPT' && (
                    <div className="absolute inset-0 bg-black/70 backdrop-blur-xs flex items-center justify-center p-4 z-50 animate-in fade-in duration-200">
                      <div className="bg-white rounded-2xl p-4 w-full max-w-xs shadow-2xl text-center space-y-3 border border-slate-200">
                        <div className="w-10 h-10 rounded-full bg-[#00843D]/10 text-[#00843D] flex items-center justify-center mx-auto">
                          <Phone className="w-5 h-5" />
                        </div>
                        <div>
                          <div className="text-xs font-bold text-slate-900">SIMULATED M-PESA STK PUSH</div>
                          <p className="text-[11px] text-slate-600 mt-1">
                            Do you want to pay KES 1,499 to <strong>USA VisaCoach (Till 882194)</strong>?
                          </p>
                        </div>

                        <input
                          type="password"
                          maxLength={4}
                          value={mpesaPin}
                          onChange={(e) => setMpesaPin(e.target.value)}
                          placeholder="Enter 4-digit PIN"
                          className="w-full text-center tracking-widest text-base font-mono py-2 rounded-lg border border-slate-300 focus:border-[#00843D] outline-hidden"
                        />

                        <div className="flex gap-2 pt-1">
                          <button
                            onClick={() => setMpesaState('IDLE')}
                            className="flex-1 py-2 text-xs font-semibold text-slate-500 bg-slate-100 rounded-lg"
                          >
                            Cancel
                          </button>
                          <button
                            onClick={submitMpesaPin}
                            className="flex-1 py-2 text-xs font-bold text-white bg-[#00843D] rounded-lg"
                          >
                            Send PIN
                          </button>
                        </div>
                      </div>
                    </div>
                  )}

                  {/* Success Screen */}
                  {mpesaState === 'SUCCESS' && (
                    <div className="absolute inset-0 bg-white flex flex-col items-center justify-center p-6 text-center z-50">
                      <div className="w-14 h-14 rounded-full bg-emerald-100 text-emerald-600 flex items-center justify-center mb-3">
                        <CheckCircle2 className="w-8 h-8" />
                      </div>
                      <h4 className="text-base font-bold text-slate-900">Payment Confirmed!</h4>
                      <p className="text-xs text-slate-500 mt-1">
                        Receipt #QHK9824LPN. Your Pro plan is now active for 30 days.
                      </p>
                    </div>
                  )}
                </div>
              )}

              {/* Android Bottom Navigation Pill */}
              <div className="w-full bg-white border-t border-slate-200 px-6 py-2.5 flex items-center justify-around text-slate-400 z-40">
                <button 
                  onClick={() => setCurrentScreen('WELCOME')}
                  className={`flex flex-col items-center gap-0.5 ${currentScreen === 'WELCOME' ? 'text-[#0A192F] font-bold' : 'hover:text-slate-700'}`}
                >
                  <Sparkles className="w-4 h-4" />
                  <span className="text-[9px]">Welcome</span>
                </button>
                <button 
                  onClick={() => setCurrentScreen('DASHBOARD')}
                  className={`flex flex-col items-center gap-0.5 ${currentScreen === 'DASHBOARD' ? 'text-[#0A192F] font-bold' : 'hover:text-slate-700'}`}
                >
                  <Smartphone className="w-4 h-4" />
                  <span className="text-[9px]">Home</span>
                </button>
                <button 
                  onClick={handleStartInterview}
                  className={`flex flex-col items-center gap-0.5 ${currentScreen === 'INTERVIEW' ? 'text-teal-600 font-bold' : 'hover:text-slate-700'}`}
                >
                  <Mic className="w-4 h-4" />
                  <span className="text-[9px]">Practice</span>
                </button>
                <button 
                  onClick={() => setCurrentScreen('PROFILE')}
                  className={`flex flex-col items-center gap-0.5 ${currentScreen === 'PROFILE' ? 'text-[#0A192F] font-bold' : 'hover:text-slate-700'}`}
                >
                  <User className="w-4 h-4" />
                  <span className="text-[9px]">Profile</span>
                </button>
              </div>

            </div>
          </div>
        </div>

        {/* Right Column: Screen Flow & DS-160 Consular Guide */}
        <div className="lg:col-span-7 space-y-4">
          
          {/* Active Screen Walkthrough Card */}
          <div className="bg-white border border-slate-200 rounded-2xl p-5 shadow-xs text-left">
            <div className="flex items-center justify-between pb-3 border-b border-slate-100">
              <div className="flex items-center gap-2">
                <span className="px-2.5 py-0.5 rounded-full text-xs font-bold bg-blue-50 text-blue-700 border border-blue-200">
                  Active Screen
                </span>
                <h3 className="font-bold text-slate-900 text-base">
                  {screensList.find(s => s.id === currentScreen)?.label}
                </h3>
              </div>
              <span className="text-xs font-mono text-slate-400">
                Screen.kt Route
              </span>
            </div>

            <div className="mt-4 text-xs text-slate-600 space-y-3">
              {currentScreen === 'WELCOME' && (
                <div className="space-y-2">
                  <p className="font-medium text-slate-800">
                    This is the authentic <strong>Welcome / Onboarding screen</strong> implemented in Kotlin Jetpack Compose (<code className="text-blue-700 font-mono">AuthScreens.kt: WelcomeScreen</code>).
                  </p>
                  <div className="bg-slate-50 p-3 rounded-xl border border-slate-200 space-y-1.5">
                    <div className="font-bold text-slate-700">Screen Capabilities:</div>
                    <ul className="list-disc pl-4 space-y-1 text-slate-600">
                      <li>Prominent branding with the USA VisaCoach emblem and microphone icon.</li>
                      <li>Clear value proposition focused on Kenyan B1/B2 tourist &amp; business visa applicants.</li>
                      <li>Two primary entry actions: <strong>"Get Started"</strong> (routes to Registration) and <strong>"I already have an account"</strong> (routes to Login).</li>
                      <li>Try tapping <strong>"Get Started"</strong> on the phone frame to experience the onboarding registration!</li>
                    </ul>
                  </div>
                </div>
              )}

              {currentScreen === 'REGISTER' && (
                <div className="space-y-2">
                  <p className="font-medium text-slate-800">
                    <strong>Registration Flow</strong> with Kenyan phone number formatting (<code className="text-blue-700 font-mono">AuthScreens.kt: RegisterScreen</code>).
                  </p>
                  <p>
                    Validates Kenyan Safaricom/Airtel numbers with the pre-styled <code className="text-[#00843D] font-bold">+254</code> prefix and prompts for the applicant's full legal name matching their passport.
                  </p>
                </div>
              )}

              {currentScreen === 'LOGIN' && (
                <div className="space-y-2">
                  <p className="font-medium text-slate-800">
                    <strong>Passwordless OTP Login</strong> (<code className="text-blue-700 font-mono">AuthScreens.kt: LoginScreen</code>).
                  </p>
                  <p>
                    Requests a one-time SMS verification code for fast, secure authentication without requiring complex passwords.
                  </p>
                </div>
              )}

              {currentScreen === 'OTP' && (
                <div className="space-y-2">
                  <p className="font-medium text-slate-800">
                    <strong>6-Digit SMS Code Verification</strong> (<code className="text-blue-700 font-mono">AuthScreens.kt: OtpScreen</code>).
                  </p>
                  <p>
                    Automatically stores the returned JWT authentication token into Android Jetpack Preferences DataStore upon successful verification.
                  </p>
                </div>
              )}

              {currentScreen === 'DASHBOARD' && (
                <div className="space-y-2">
                  <p className="font-medium text-slate-800">
                    <strong>Candidate Command Center</strong> (<code className="text-blue-700 font-mono">DashboardScreen.kt</code>).
                  </p>
                  <p>
                    Features the INA 214(b) readiness progress gauge (84%), quick drill recommendations, and immediate one-tap access to launch a live Consular Officer interview.
                  </p>
                </div>
              )}

              {currentScreen === 'INTERVIEW' && (
                <div className="space-y-2">
                  <p className="font-medium text-slate-800">
                    <strong>Real-Time Voice Mock Interview</strong> (<code className="text-blue-700 font-mono">RealTimeInterviewScreen.kt</code>).
                  </p>
                  <p>
                    Simulates the WebSocket audio stream with the AI Consular Officer. Shows speech turn state machines (SPEAKING vs. LISTENING) and audio waveform feedback.
                  </p>
                </div>
              )}

              {currentScreen === 'RESULTS' && (
                <div className="space-y-2">
                  <p className="font-medium text-slate-800">
                    <strong>INA 214(b) Evaluation Report</strong> (<code className="text-blue-700 font-mono">InterviewResultsScreen.kt</code>).
                  </p>
                  <p>
                    Evaluates applicant responses across 6 core consular pillars: Ties to Kenya, Financial Solvency, DS-160 Consistency, Answer Conciseness, Purpose Specificity, and Confidence.
                  </p>
                </div>
              )}

              {currentScreen === 'PROFILE' && (
                <div className="space-y-2">
                  <p className="font-medium text-slate-800">
                    <strong>DS-160 Alignment Profile</strong> (<code className="text-blue-700 font-mono">ProfileScreen.kt</code>).
                  </p>
                  <p>
                    Allows applicants to specify their intended U.S. travel dates, host details, and employment history so the AI Consular Officer tailors questions specifically to their background.
                  </p>
                </div>
              )}

              {currentScreen === 'MPESA' && (
                <div className="space-y-2">
                  <p className="font-medium text-slate-800">
                    <strong>Safaricom Daraja STK Push Integration</strong> (<code className="text-blue-700 font-mono">MpesaPaymentScreen.kt</code>).
                  </p>
                  <p>
                    Triggers a real-time Lipa na M-Pesa STK Push to the applicant's handset and polls the Spring Boot backend until the callback confirms payment.
                  </p>
                </div>
              )}
            </div>
          </div>

          {/* Quick Screen Selector Grid */}
          <div className="bg-white border border-slate-200 rounded-2xl p-5 shadow-xs text-left">
            <h4 className="text-xs font-bold text-slate-700 uppercase tracking-wider mb-3">
              Explore All Android Screen Flows:
            </h4>
            <div className="grid grid-cols-2 sm:grid-cols-3 gap-2 text-xs">
              {screensList.map((s) => {
                const IconComponent = s.icon;
                const isCurrent = currentScreen === s.id;
                return (
                  <button
                    key={s.id}
                    onClick={() => setCurrentScreen(s.id)}
                    className={`p-3 rounded-xl border text-left flex items-center justify-between transition-all ${
                      isCurrent 
                        ? 'border-teal-500 bg-teal-50/50 shadow-xs' 
                        : 'border-slate-200 bg-white hover:border-slate-300'
                    }`}
                  >
                    <div className="flex items-center gap-2 overflow-hidden">
                      <IconComponent className={`w-4 h-4 shrink-0 ${isCurrent ? 'text-teal-600' : 'text-slate-400'}`} />
                      <span className={`truncate ${isCurrent ? 'font-bold text-[#0A192F]' : 'font-medium text-slate-700'}`}>
                        {s.label}
                      </span>
                    </div>
                    <ChevronRight className={`w-3.5 h-3.5 shrink-0 ${isCurrent ? 'text-teal-600' : 'text-slate-300'}`} />
                  </button>
                );
              })}
            </div>
          </div>

          {/* Android Studio Deployment Reference */}
          <div className="bg-slate-50 border border-slate-200 rounded-2xl p-4 text-left text-xs text-slate-600 flex items-start gap-3">
            <div className="w-8 h-8 rounded-xl bg-blue-100 text-blue-800 flex items-center justify-center shrink-0 mt-0.5">
              <Compass className="w-4 h-4" />
            </div>
            <div>
              <div className="font-bold text-slate-900">Native Android Studio Source Code</div>
              <p className="mt-0.5 leading-relaxed text-slate-600">
                All 16 Kotlin screens, ViewModels, and Room entities are fully configured under <code className="text-slate-800 font-mono bg-white px-1.5 py-0.5 rounded border border-slate-200">/android</code>. You can build the native APK directly by opening the <code className="text-slate-800 font-mono bg-white px-1.5 py-0.5 rounded border border-slate-200">android/</code> directory in Android Studio.
              </p>
            </div>
          </div>

        </div>

      </div>
    </div>
  );
};
