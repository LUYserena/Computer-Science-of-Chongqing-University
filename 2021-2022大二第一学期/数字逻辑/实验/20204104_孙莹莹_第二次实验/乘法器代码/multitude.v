`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2021/11/11 18:56:44
// Design Name: 
// Module Name: multitude
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module multitude(
    input clk,
    input wire [7:0] a,
    input wire [7:0] b,
    output [15:0] out,
    output [3:0] sm_wei,
    output [7:0] sm_duan
 );
 
    mutl_machine tryMachine(a,b,out);
    show tryShow(clk,out,sm_wei,sm_duan);
endmodule
